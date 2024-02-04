package com.vobbla16.mesh.ui.screens.scheduleScreen

import com.vobbla16.mesh.ui.ViewAction
import kotlinx.datetime.LocalDate

sealed class ScheduleScreenAction : ViewAction {
    data object NavigateToLoginScreen : ScheduleScreenAction()
    data class UpdateDataPickerState(val newDate: LocalDate) : ScheduleScreenAction()
}