package com.vobbla16.mesh.common

import android.annotation.SuppressLint
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

@SuppressLint("NewApi")
fun fromMeshStr2LocalDT(str: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    return java.time.LocalDateTime.parse(str, formatter).toKotlinLocalDateTime()
}

@SuppressLint("NewApi")
fun fromMeshStr2LocalDate(str: String): LocalDate {
    val formatter = DateTimeFormatterBuilder()
        .appendPattern("dd.MM.yyyy")
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .toFormatter()
    return java.time.LocalDateTime.parse(str, formatter).toKotlinLocalDateTime().date
}