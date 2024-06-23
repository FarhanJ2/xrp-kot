package frc.robot.commands

import edu.wpi.first.wpilibj.RobotState
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.RobotContainer
import frc.robot.subsystems.XRPDrivetrain
import frc.robot.utils.Movement
import frc.robot.utils.MovementRecorder

class DriveCommand(private val getForward: () -> Double, private val getRotation: () -> Double, private val runTime: Double?) : Command() {
    private val xRPDrivetrain = XRPDrivetrain
    private var startTime: Long = 0


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(xRPDrivetrain)
    }

    override fun initialize() {
        startTime = System.currentTimeMillis()
    }

    override fun execute() {
        val dir = getForward()
        val rot = getRotation()
        val duration = 0.02 // 20ms

        xRPDrivetrain.arcadeDrive(dir, rot)
        if (RobotContainer.recordPath && !RobotState.isAutonomous()) {
            MovementRecorder.recordMovement(dir, rot, duration)
        }
    }

    override fun isFinished(): Boolean {
        // TODO: Make this return true when this Command no longer needs to run execute()
        // this is for auton else make runTime null so it can work in teleop
        return runTime != null && (System.currentTimeMillis() - startTime) >= runTime * 1000
    }

    override fun end(interrupted: Boolean) {
        xRPDrivetrain.arcadeDrive(0.0, 0.0) // reset speed

        // TODO CHECK IF IT IS NOT AUTON AND IF ANOTHER FLAG IS ON FOR IT TO SAVE
        try {
            MovementRecorder.saveMovementsToFile()
        } catch (error: Error) {
            println("Error saving path $error")
        }
    }
}
