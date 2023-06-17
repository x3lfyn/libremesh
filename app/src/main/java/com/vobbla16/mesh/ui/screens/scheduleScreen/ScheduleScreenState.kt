package com.vobbla16.mesh.ui.screens.scheduleScreen

import com.vobbla16.mesh.domain.model.schedule.Schedule
import com.vobbla16.mesh.ui.ViewState

data class ScheduleScreenState(
    val schedule: Schedule?,
    val isLoading: Boolean,
    val error: String?,
    val datePickerOpened: Boolean
) : ViewState