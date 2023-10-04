package com.vobbla16.mesh.ui.screens.scheduleScreen

import kotlinx.datetime.LocalDate

data class ScheduleScreenState(
    val datePickerOpened: Boolean,
    val selectedDate: LocalDate
)