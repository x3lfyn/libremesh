package com.vobbla16.mesh.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Int.secsToLocalTime(): LocalTime {
    return Instant.fromEpochSeconds(this.toLong())
        .toLocalDateTime(TimeZone.currentSystemDefault()).time
}