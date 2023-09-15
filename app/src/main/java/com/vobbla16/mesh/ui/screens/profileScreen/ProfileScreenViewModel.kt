package com.vobbla16.mesh.ui.screens.profileScreen

import android.webkit.CookieManager
import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.LoadingOrDone
import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.common.toText
import com.vobbla16.mesh.domain.use_case.GetStudentUseCase
import com.vobbla16.mesh.domain.use_case.LogOutUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val getStudentUseCase: GetStudentUseCase,
    private val logOutUseCase: LogOutUseCase
) : BaseViewModel<ProfileScreenState, ProfileScreenAction>() {
    override fun setInitialState() = ProfileScreenState(
        profile = null,
        isLoading = true,
        error = null,
        dialogOpened = false,
        isLoggingOut = false
    )

    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        getStudentUseCase().onEach {
            when(it) {
                is OrLoading.Loading -> {
                    setState { copy(isLoading = true) }
                }
                is OrLoading.Data -> {
                    when(it.res) {
                        is Resource.Ok -> {
                            setState { copy(profile = it.res.data.children[0], isLoading = false, error = null) }
                        }
                        is Resource.Err -> {
                            setState { copy(error = it.res.e.toText(), isLoading = false) }
                        }
                        is Resource.NotLoggedIn -> {
                            setAction { ProfileScreenAction.NavigateToLoginScreen }
                        }
                    }
                }
            }
        }.collect()
    }

    fun requestLogOut() = viewModelScope.launch {
        CookieManager.getInstance().removeAllCookies(null)
        logOutUseCase().onEach {
            when (it) {
                is LoadingOrDone.Loading -> {
                    setState { copy(isLoggingOut = true) }
                }

                is LoadingOrDone.Done -> {
                    setAction { ProfileScreenAction.RestartAfterTokenReset }
                    setState { copy(isLoggingOut = false, dialogOpened = false) }
                }
            }
        }.collect()
    }

    fun updatedDialogOpened(isOpened: Boolean) = setState { copy(dialogOpened = isOpened) }
}