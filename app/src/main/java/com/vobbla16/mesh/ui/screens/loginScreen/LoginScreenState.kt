package com.vobbla16.mesh.ui.screens.loginScreen

data class LoginScreenState(
    val processingState: ProcessingState
)

sealed class ProcessingState {
    object WelcomeStep : ProcessingState()
    object WebViewStep : ProcessingState()
    object ProcessingStep : ProcessingState()
    data class Error(val message: String) : ProcessingState()
}