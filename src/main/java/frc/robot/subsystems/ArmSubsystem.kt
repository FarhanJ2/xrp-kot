package frc.robot.subsystems

import edu.wpi.first.wpilibj.xrp.XRPServo
import edu.wpi.first.wpilibj2.command.SubsystemBase;

object ArmSubsystem : SubsystemBase() {
    private val m_armMotor = XRPServo(4)

    fun setPosition() {
        m_armMotor.setPosition(0.0)
    }

    override fun periodic()
    {
        // This method will be called once per scheduler run
    }

    override fun simulationPeriodic()
    {
        // This method will be called once per scheduler run during simulation
    }
}