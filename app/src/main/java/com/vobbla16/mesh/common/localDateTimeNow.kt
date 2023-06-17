package com.vobbla16.mesh.common

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun localDateTimeNow(): LocalDateTime =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())