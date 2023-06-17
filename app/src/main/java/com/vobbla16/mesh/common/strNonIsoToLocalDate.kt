package com.vobbla16.mesh.common

import kotlinx.datetime.LocalDate

fun String.nonIsoToLocalDate(): LocalDate {
    val parts = this.split(".")
    return LocalDate(parts[2].toInt(), parts[1].toInt(), parts[0].toInt())
}