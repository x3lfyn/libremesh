package com.vobbla16.mesh.ui.screens.loginScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.DataOrError
import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.toText
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
                    is OrLoading.Loading -> {
                        setState { copy(processingState = ProcessingState.ProcessingStep) }
                    }

                    is OrLoading.Data -> {
                        when (it.res) {
                            is DataOrError.Success -> {
                                settingsRepository.setToken(it.res.data)
                                setAction { LoginScreenAction.SetSavedStateFlag }
                                setAction { LoginScreenAction.GoBack }
                            }

                            is DataOrError.Error -> {
                                setState {
                                    copy(processingState = ProcessingState.Error(it.res.e.toText()))
                                }
                            }
                        }
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