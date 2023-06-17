package com.vobbla16.mesh.common

import kotlinx.datetime.LocalDate

fun LocalDate.toStr(): String =
    "${this.dayOfMonth}.${this.monthNumber}.${this.year}"