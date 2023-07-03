package com.vobbla16.mesh.ui.screens.loginScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.domain.repository.SettingsRepository
import com.vobbla16.mesh.domain.use_case.OauthCodeToTokenUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val oauthCodeToTokenUseCase: OauthCodeToTokenUseCase,
    private val settingsRepository: SettingsRepository
) :
    BaseViewModel<LoginScreenState, LoginScreenAction>() {

    override fun setInitialState() =
        LoginScreenState(processingState = ProcessingState.WelcomeStep)

    fun processCode(code: String) {
        viewModelScope.launch {
            oauthCodeToTokenUseCase(code).onEach {
                when (it) {
                    is Resource.Success -> {
                        settingsRepository.setToken(it.data)
                        setAction { LoginScreenAction.SetSavedStateFlag }
                        setAction { LoginScreenAction.GoBack }
                    }

                    is Resource.Loading -> {
                        setState { copy(processingState = ProcessingState.ProcessingStep) }
                    }

                    is Resource.Error -> {
                        setState { copy(processingState = ProcessingState.Error(it.message)) }
                    }
                }
            }.collect()
        }
    }

    fun toWebViewStep() {
        setState { copy(processingState = ProcessingState.WebViewStep) }
    }

    fun backFromWebViewStep() {
        setState { copy(processingState = ProcessingState.WelcomeStep) }
    }
}