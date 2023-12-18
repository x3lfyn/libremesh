package com.vobbla16.mesh.common

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun LocalDate.toEpochSecond(secondOfDay: Int = 43200): Long = LocalDateTime(this, LocalTime.fromSecondOfDay(secondOfDay)).toInstant(TimeZone.currentSystemDefault()).epochSeconds