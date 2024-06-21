package frc.robot.commands


import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.ArmSubsystem

// TODO: Add your sequential commands in the super constructor call,
//       e.g. SequentialCommandGroup(OpenClawCommand(), MoveArmCommand())
class ToTableLegAutoCommandGroup : SequentialCommandGroup() {
    init {
        addCommands(
            DriveCommand(1.0, 0.0, 0.6),
            DriveCommand(0.8, -0.8, 1.5),
            DriveCommand(-0.8, -0.4, 1.5),
            DriveCommand(-0.9, -0.0, 0.8),
        )
    }
}