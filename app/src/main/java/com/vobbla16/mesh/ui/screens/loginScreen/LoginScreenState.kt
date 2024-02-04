package com.vobbla16.mesh.ui.screens.loginScreen

import com.vobbla16.mesh.ui.ViewState

data class LoginScreenState(
    val processingState: ProcessingState
) : ViewState

sealed class ProcessingState {
    data object WelcomeStep : ProcessingState()
    data object WebViewStep : ProcessingState()
    data object ProcessingStep : ProcessingState()
    data class Error(val message: String) : ProcessingState()
}