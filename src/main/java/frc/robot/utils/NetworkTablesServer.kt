package frc.robot.utils

import edu.wpi.first.networktables.BooleanSubscriber
import edu.wpi.first.networktables.DoubleSubscriber
import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import java.net.InetAddress

class NetworkTablesServer {
    private var ntInstance: NetworkTableInstance = NetworkTableInstance.getDefault()

    private val controls: NetworkTable = ntInstance.getTable("controls")
    private val dir: DoubleSubscriber
    private val rot: DoubleSubscriber
    private val robotEnabled: BooleanSubscriber

    // primitives
    var dirVal: Double = 0.0
        private set
    var rotVal: Double = 0.0
        private set
    var robotEnabledVal: Boolean = false
        private set

    private fun init() {
        try {
            val ip = InetAddress.getLocalHost()
            println("IP ADDR NT4 " + ip.hostAddress + ":5810")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        init()

        dir = controls.getDoubleTopic("dir").subscribe(0.0)
        rot = controls.getDoubleTopic("rot").subscribe(0.0)
        robotEnabled = controls.getBooleanTopic("robotEnabled").subscribe(false)
    }

    fun updateValuesPeriodic() {
        dirVal = dir.get()
        rotVal = rot.get()
        robotEnabledVal = robotEnabled.get()

//        println("Dir: $dirVal Rot: $rotVal")
    }

    fun close() {
        dir.close()
        rot.close()
    }
}
