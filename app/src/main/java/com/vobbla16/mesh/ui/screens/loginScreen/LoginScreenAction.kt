package com.vobbla16.mesh.ui.screens.loginScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class LoginScreenAction : ViewAction {
    object SetSavedStateFlag : LoginScreenAction()
    object GoBack : LoginScreenAction()
}