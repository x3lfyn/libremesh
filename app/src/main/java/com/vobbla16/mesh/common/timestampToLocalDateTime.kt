package com.vobbla16.mesh.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.secsToLocalTime(): LocalDateTime {
    return Instant.fromEpochSeconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}