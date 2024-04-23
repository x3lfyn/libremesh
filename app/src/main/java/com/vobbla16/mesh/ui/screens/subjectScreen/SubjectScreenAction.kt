package com.vobbla16.mesh.ui.screens.subjectScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class SubjectScreenAction: ViewAction {
    data object NavigateToLoginScreen : SubjectScreenAction()
}