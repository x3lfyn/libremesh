package com.vobbla16.mesh.ui.screens.homeworkScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class HomeworkScreenAction: ViewAction {
    object NavigateToLoginScreen : HomeworkScreenAction()
}