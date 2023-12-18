package com.vobbla16.mesh.common

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus

fun firstDayOfWeek(day: LocalDate): LocalDate {
    val curDayNum = day.dayOfWeek.isoDayNumber

    return day.minus(DatePeriod(days = curDayNum - 1))
}