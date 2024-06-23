package frc.robot

/*
 * The Constants file provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This file should not be used for any other purpose.
 * All String, Boolean, and numeric (Int, Long, Float, Double) constants should use
 * `const` definitions. Other constant types should use `val` definitions.
 */

class Constants {
    class DriverConstants {
        companion object DriverController {
            const val K_DRIVER_PORT = 0
            const val K_DRIVER_RAW_AXIS_LEFT = 1
            const val K_DRIVER_RAW_AXIS_RIGHT = 4
        }
    }

    class RecorderConstants {
        companion object OffsetError {
            const val OFFSET_ERROR = 1.5259021893143654E-5
        }
    }
}