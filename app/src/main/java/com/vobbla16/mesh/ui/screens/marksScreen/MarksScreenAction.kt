package com.vobbla16.mesh.ui.screens.marksScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class MarksScreenAction: ViewAction {
    object NavigateToLoginScreen : MarksScreenAction()
}