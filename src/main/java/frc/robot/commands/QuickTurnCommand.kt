package frc.robot.commands

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.XRPDrivetrain

class QuickTurnCommand(private val dir: Double, private val duration: Double) : CommandBase() {
    private val xRPDrivetrain = XRPDrivetrain
    private val timer = Timer()

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(xRPDrivetrain)
    }

    override fun initialize() {
        timer.reset()
        timer.start()
    }

    override fun execute() {
        xRPDrivetrain.arcadeDrive(0.0, dir)
    }

    override fun isFinished(): Boolean {
        return timer.get() >= duration
    }

    override fun end(interrupted: Boolean) {
        xRPDrivetrain.arcadeDrive(0.0, 0.0)
        timer.stop()
    }
}
