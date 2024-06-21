package frc.robot.commands

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.XRPDrivetrain

/** An example command that uses the [XRPDrivetrain] subsystem. */
class ExampleCommand() : Command()
{
    init
    {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(XRPDrivetrain)
    }

    // Called when the command is initially scheduled.
    override fun initialize()
    {
    }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute()
    {
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean)
    {
    }

    // Returns true when the command should end.
    override fun isFinished(): Boolean
    {
        return false
    }
}