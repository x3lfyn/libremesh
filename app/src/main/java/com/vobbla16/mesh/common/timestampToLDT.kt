package com.vobbla16.mesh.common

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun msToLocalDateTime(ms: Long, tz: TimeZone = TimeZone.currentSystemDefault()) =
    Instant.fromEpochMilliseconds(ms)
        .toLocalDateTime(tz)

fun secsToLocalDateTime(secs: Long, tz: TimeZone = TimeZone.currentSystemDefault()) =
    msToLocalDateTime(secs * 1000, tz)

fun secsToLocalDateTime(secs: Double, tz: TimeZone = TimeZone.currentSystemDefault()) =
    msToLocalDateTime((secs * 1000).toLong(), tz)
