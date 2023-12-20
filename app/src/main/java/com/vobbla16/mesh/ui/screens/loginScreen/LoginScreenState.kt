package com.vobbla16.mesh.ui.screens.loginScreen

import com.vobbla16.mesh.ui.ViewState

data class LoginScreenState(
    val processingState: ProcessingState
) : ViewState

sealed class ProcessingState {
    object WelcomeStep : ProcessingState()
    object WebViewStep : ProcessingState()
    object ProcessingStep : ProcessingState()
    data class Error(val message: String) : ProcessingState()
}