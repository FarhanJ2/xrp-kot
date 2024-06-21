package frc.robot.subsystems

import edu.wpi.first.wpilibj.xrp.XRPRangefinder
import edu.wpi.first.wpilibj2.command.SubsystemBase;

object UltrasoundSubsystem : SubsystemBase() {
    private val sensor = XRPRangefinder()

    fun getDistance(): Double {
        return sensor.distanceInches * 2.54f
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