package com.vobbla16.mesh.ui.screens.loginScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class LoginScreenAction : ViewAction {
    data object SetSavedStateFlag : LoginScreenAction()
    data object GoBack : LoginScreenAction()
}