package com.vobbla16.mesh.common

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

fun weekFromFirstDay(firstDayOfWeek: LocalDate): List<LocalDate> {
    val ans = mutableListOf<LocalDate>()
    for (i in 0..6) {
        ans += firstDayOfWeek.plus(DatePeriod(days = i))
    }

    return ans.toList()
}