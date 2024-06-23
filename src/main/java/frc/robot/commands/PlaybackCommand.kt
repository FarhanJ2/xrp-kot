package frc.robot.commands

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.subsystems.XRPDrivetrain
import frc.robot.utils.Movement
import frc.robot.utils.MovementRecorder
import edu.wpi.first.wpilibj.Timer
import frc.robot.Constants

class PlaybackCommand(private val fileName: String, ) : Command() {
    private val xRPDrivetrain = XRPDrivetrain

    private lateinit var movements: List<Movement>
    private var currentMovementIndex = 0
    private var movementStartTime = 0.0

    init {
        addRequirements(xRPDrivetrain)
    }

    override fun initialize() {
        movements = MovementRecorder.loadMovementsFromFile("E:\\Programming\\robotics\\xrp-kot\\src\\main\\java\\frc\\robot\\paths\\$fileName")
        currentMovementIndex = 0
        movementStartTime = Timer.getFPGATimestamp()
    }

    override fun execute() {
        if (currentMovementIndex < movements.size) {
            val movement = movements[currentMovementIndex]
            val dir = movement.dir
            val rot = movement.rot
            val duration = movement.duration
            val timeDelta = movement.timeDelta

            if (Timer.getFPGATimestamp() - movementStartTime >= timeDelta) {
                XRPDrivetrain.arcadeDrive(dir, rot)
                currentMovementIndex++
                movementStartTime = Timer.getFPGATimestamp()
            }
        }
    }

    override fun isFinished(): Boolean {
        return currentMovementIndex >= movements.size
    }

    override fun end(interrupted: Boolean) {
        println("Auton End")
        xRPDrivetrain.arcadeDrive(0.0, 0.0) // stop
    }
}
