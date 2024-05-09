package com.vobbla16.mesh.common

import kotlinx.datetime.LocalDate

fun LocalDate.toStr(): String =
    "${this.dayOfMonth.toString().padStart(2, '0')}.${this.monthNumber.toString().padStart(2, '0')}.${this.year}"