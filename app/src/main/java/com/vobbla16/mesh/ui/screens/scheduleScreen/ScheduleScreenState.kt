package com.vobbla16.mesh.ui.screens.scheduleScreen

import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import com.vobbla16.mesh.ui.ViewState

data class ScheduleScreenState(
    val schedule: ScheduleModel?,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val error: Throwable?,
    val datePickerOpened: Boolean
) : ViewState