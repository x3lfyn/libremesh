package com.vobbla16.mesh.common.structures

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toHumanStr() = "${this.date.dayOfMonth.toString().padStart(2, '0')}.${this.date.monthNumber.toString().padStart(2, '0')}.${this.date.year} ${this.time.hour.toString().padStart(2, '0')}:${this.time.minute.toString().padStart(2, '0')}"