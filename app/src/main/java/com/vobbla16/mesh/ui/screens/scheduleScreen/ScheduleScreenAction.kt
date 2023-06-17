package com.vobbla16.mesh.ui.screens.scheduleScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class ScheduleScreenAction : ViewAction {
    object NavigateToLoginScreen : ScheduleScreenAction()
}