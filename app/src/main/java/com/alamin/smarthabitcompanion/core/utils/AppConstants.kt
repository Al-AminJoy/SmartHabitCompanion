package com.alamin.smarthabitcompanion.core.utils

import androidx.compose.ui.graphics.Color

object AppConstants {
    const val DATABASE_NAME = "smart_habit_db"
    const val DATABASE_VERSION = 1
    const val APP_MARGIN: Int = 8
    const val ENABLE_LOG = true

    const val ADD_HABIT_NAME_TEXT_LIMIT = 24
    const val ADD_HABIT_TARGET_TEXT_LIMIT = 6
    const val ADD_HABIT_UNIT_TEXT_LIMIT = 16

    val chartColor = arrayListOf<Color>(
        Color(0xFF003f5c),
        Color(0xFF374c80),
        Color(0xFF7a5195),
        Color(0xFFbc5090),
        Color(0xFFef5675),
        Color(0xFFff764a),
        Color(0xFFffa600),
    )

}