package frc.robot

import edu.wpi.first.wpilibj.RobotState
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.commands.*
import frc.robot.subsystems.UltrasoundSubsystem
import frc.robot.subsystems.XRPDrivetrain
import frc.robot.utils.MovementRecorder
import frc.robot.utils.NetworkTablesServer

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
object RobotContainer {
    val ntServer = NetworkTablesServer()

//    val test = ntServer.ntInstance.

    private val m_driverController = CommandXboxController(Constants.DriverConstants.K_DRIVER_PORT)
    var recordPath = false

    private val autoModeChooser = SendableChooser<AutoMode>().apply {
        AutoMode.values().forEach { addOption(it.optionName, it) }
        setDefaultOption(AutoMode.default.optionName, AutoMode.default)
    }

    /**
     * A enumeration of the available autonomous modes.
     *
     * @param optionName The name for the [autoModeChooser] option.
     * @param command The [Command] to run for this mode.
     */
    private enum class AutoMode(val optionName: String, val command: Command) {
        // TODO: Replace with real auto modes and their corresponding commands
        CUSTOM_AUTO_1("To Desk Drawer from Table Leg", ToDrawerAutoCommand()),
        CUSTOM_AUTO_2("To Table Leg", ToTableLegAutoCommandGroup()),
        CUSTOM_AUTO_3("Test PathPlanner", PlaybackCommand("2024-06-23_14-14-02-path.json")),
        CUSTOM_AUTO_4("Trash Can to Facing Bed", PlaybackCommand("2024-06-23_14-17-17-path.json")),
        CUSTOM_AUTO_5("Test Path", PlaybackCommand("2024-06-28_01-07-49-path.json"));

        companion object {
            /** The default auto mode. */
            val default = CUSTOM_AUTO_1
        }
    }

    /** The command to run in autonomous. */
    val selectedAutonomousCommand: Command
        get() = autoModeChooser.selected?.command ?: AutoMode.default.command

    init {
        if (RobotState.isTeleop())
        {
            XRPDrivetrain.defaultCommand =
                DriveCommand({ -m_driverController.getRawAxis(Constants.DriverConstants.K_DRIVER_RAW_AXIS_LEFT) }, { -m_driverController.getRawAxis(Constants.DriverConstants.K_DRIVER_RAW_AXIS_RIGHT) }, null)
        }

        SmartDashboard.putData("Auto choices", autoModeChooser)
        SmartDashboard.putBoolean("Record Path?", recordPath)
        configureButtonBindings()
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a [GenericHID][edu.wpi.first.wpilibj.GenericHID] or one of its subclasses such
     * as [Joystick][edu.wpi.first.wpilibj.Joystick] or [XboxController][edu.wpi.first.wpilibj.XboxController],
     * and then passing it to a [JoystickButton][edu.wpi.first.wpilibj2.command.button.JoystickButton].
     */
    private fun configureButtonBindings() {
        // TODO: Add button to command mappings here.
        //       See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
        m_driverController.a().whileTrue(InstantCommand({
            println("Object Distance: ${UltrasoundSubsystem.getDistance()}")
        }))

        m_driverController.b().whileTrue(InstantCommand({
            if (recordPath) {
                MovementRecorder.saveMovementsToFile()
            }
            recordPath = !recordPath
            println("Set recording status to $recordPath")
        }))


        m_driverController.leftTrigger().onTrue(QuickTurnCommand(1.0, .25))
        m_driverController.rightTrigger().onTrue(QuickTurnCommand(-1.0, .25))
        m_driverController.leftBumper().onTrue(QuickTurnCommand(1.0, .5))
        m_driverController.rightBumper().onTrue(QuickTurnCommand(-1.0, .5))
    }
}
