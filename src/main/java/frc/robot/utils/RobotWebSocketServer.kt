package frc.robot.utils

import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.NetworkTableInstance
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class RobotWebSocketServer(address: InetSocketAddress?) : WebSocketServer(address) {
    private val moveCommand: NetworkTableEntry

    init {
        val ntInstance = NetworkTableInstance.getDefault()
        val table = ntInstance.getTable("robotCommands")
        moveCommand = table.getEntry("moveCommand")

        println("Starting server")
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        println("New connection: " + conn.remoteSocketAddress)
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        println("Closed connection: " + conn.remoteSocketAddress)
    }

    override fun onMessage(conn: WebSocket, message: String) {
        println("Received message: $message")
        moveCommand.setString(message)
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        ex.printStackTrace()
    }

    override fun onStart() {
        println("Server started successfully")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val host = "0.0.0.0"
            val port = 8887 // u can change the port if needed
            val server = RobotWebSocketServer(InetSocketAddress(host, port))
            server.start()
            println("WebSocket server started on port: $port")
        }
    }
}
