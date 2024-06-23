package frc.robot.utils

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.robot.Constants
import java.io.File
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

@Serializable
data class Movement(val dir: Double, val rot: Double, val duration: Double, val timeDelta: Double)

object MovementRecorder {
    private val movements = mutableListOf<Movement>()
    private var lastRecordTime = Timer.getFPGATimestamp()

    fun recordMovement(dir: Double, rot: Double, duration: Double) {
        val currentTime = Timer.getFPGATimestamp()
        val timeDelta = currentTime - lastRecordTime

        // Apply threshold to treat near-zero values as zero
        val adjustedDir = if (dir == Constants.RecorderConstants.OFFSET_ERROR) 0.0 else dir
        val adjustedRot = if (rot == -Constants.RecorderConstants.OFFSET_ERROR) 0.0 else rot

        println("Recording - Dir: $adjustedDir, Rot: $adjustedRot, Duration: $duration, TimeDelta: $timeDelta")
        movements.add(Movement(adjustedDir, adjustedRot, duration, timeDelta))
        lastRecordTime = currentTime
    }

    fun saveMovementsToFile() {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
        val formattedDateTime = currentDateTime.format(formatter)
        val fileName = "E:/Programming/robotics/xrp-kot/src/main/java/frc/robot/paths/$formattedDateTime-path.json"

        val file = File(fileName)
        val json = Json { prettyPrint = true }
        file.writeText(json.encodeToString(movements))
        SmartDashboard.putString("Value saved to", file.absolutePath)
    }

    fun loadMovementsFromFile(filePath: String): List<Movement> {
        val fileContent = File(filePath).readText(Charsets.UTF_8)
        return Json.decodeFromString(fileContent)
    }

}