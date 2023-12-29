package com.vobbla16.mesh

import com.vobbla16.mesh.common.firstDayOfWeek
import com.vobbla16.mesh.common.msToLocalDateTime
import com.vobbla16.mesh.common.secsToLocalDateTime
import com.vobbla16.mesh.common.toEpochSecond
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import org.junit.Test

class CommonFunctionsTest {
    @Test
    fun firstDayOfWeekIsCorrect() { // should return monday of week in which the given day is
        val day1 = LocalDate(2040, 11, 14) // wednesday
        val ans1 = LocalDate(2040, 11, 12)

        val day2 = LocalDate(2025, 5, 1) // thursday
        val ans2 = LocalDate(2025, 4, 28)

        val day3 = LocalDate(2027, 4, 19) // monday
        val ans3 = day3

        assert(firstDayOfWeek(day1) == ans1)
        assert(firstDayOfWeek(day2) == ans2)
        assert(firstDayOfWeek(day3) == ans3)
    }

    @Test
    fun timestampToLocalDateTimeIsCorrect() { // should convert unix timestamp to kotlinx.datetime LocalDateTime
        val ts1 = 2551268235 * 1000
        val ans1 = LocalDateTime(2050, 11, 5, 13, 37, 15)

        val ts2 = 5278179661 * 1000
        val ans2 = LocalDateTime(2137, 4, 5, 1, 1, 1)

        assert(msToLocalDateTime(ts1) == ans1)
        assert(secsToLocalDateTime(ts1 / 1000) == ans1)

        assert(msToLocalDateTime(ts2) == ans2)
        assert(secsToLocalDateTime(ts2 / 1000) == ans2)
    }

    @Test
    fun localDateToEpochSecondIsCorrect() { // should convert kotlinx.datetime LocalDate to unix epoch seconds
        val day1 = LocalDate(2050, 11, 5)
        val secondOfDay1 = 1000
        val ans1 = 2551219200 + secondOfDay1

        val day2 = LocalDate(2137, 4,5)
        val secondOfDay2 = 1337
        val ans2 = 5278176000 + secondOfDay2

        assert(day1.toEpochSecond(secondOfDay1, TimeZone.UTC) == ans1)
        assert(day2.toEpochSecond(secondOfDay2, TimeZone.UTC) == ans2)
    }
}