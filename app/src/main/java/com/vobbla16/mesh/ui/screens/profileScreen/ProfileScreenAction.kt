package com.vobbla16.mesh.ui.screens.profileScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class ProfileScreenAction : ViewAction {
    data object NavigateToLoginScreen : ProfileScreenAction()
    data object RestartAfterTokenReset : ProfileScreenAction()
}