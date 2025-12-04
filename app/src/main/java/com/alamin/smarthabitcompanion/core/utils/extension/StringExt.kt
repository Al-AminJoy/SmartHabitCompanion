package com.alamin.smarthabitcompanion.core.utils.extension

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.checkIsAfter(deadline: String): Boolean {

    val currentDate = this

    val initialStartDate = deadline.split("-")
    var startDay = initialStartDate[2]
    var startMonth = initialStartDate[1]
    val startYear = initialStartDate[0]

    if (startDay.length == 1){
        startDay = "0${startDay}"
    }

    if (startMonth.length == 1){
        startMonth = "0${startMonth}"
    }

    val furnishedStartDate = "${startYear}-${startMonth}-${startDay}"

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val startDate = LocalDate.parse(furnishedStartDate, formatter)
    val oneDayBefore = startDate.minusDays(1)

    val targetTime = LocalDate.parse(currentDate, formatter)

    return targetTime.isAfter(oneDayBefore)
}

fun String.checkIsBefore(deadline: String): Boolean {

    val currentDate = this

    val initialBeforeDate = deadline.split("-")
    var beforeDay = initialBeforeDate[2]
    var beforeMonth = initialBeforeDate[1]
    val beforeYear = initialBeforeDate[0]

    if (beforeDay.length == 1){
        beforeDay = "0${beforeDay}"
    }

    if (beforeMonth.length == 1){
        beforeMonth = "0${beforeMonth}"
    }

    val furnishedBeforeDate = "${beforeYear}-${beforeMonth}-${beforeDay}"

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val beforeDate = LocalDate.parse(furnishedBeforeDate, formatter)
    val oneDayAfter = beforeDate.plusDays(1)

    val targetDate = LocalDate.parse(currentDate, formatter)

    return targetDate.isBefore(oneDayAfter)
}

fun String?.formatTime(): String {
    return try {
        val instant = Instant.parse(this)
        val zonedDateTime =
            ZonedDateTime.ofInstant(instant, ZoneOffset.systemDefault())
        DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm").format(zonedDateTime)
    } catch (e: Exception) {
        "Unknown date"
    }
}