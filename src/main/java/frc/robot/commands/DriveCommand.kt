package frc.robot.commands

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.XRPDrivetrain
import kotlin.system.*

class DriveCommand(private val forward: Double, private val rotation: Double, private val runTime: Double) : Command() {
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
        xRPDrivetrain.arcadeDrive(forward, rotation)
    }

    override fun isFinished(): Boolean {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return (System.currentTimeMillis() - startTime) >= runTime * 1000 // runTime is in seconds
    }

    override fun end(interrupted: Boolean) {
        xRPDrivetrain.arcadeDrive(0.0, 0.0) // reset speed
    }
}
