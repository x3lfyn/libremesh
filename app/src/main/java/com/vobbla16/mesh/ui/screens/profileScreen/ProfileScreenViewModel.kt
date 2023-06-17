package com.vobbla16.mesh.ui.screens.profileScreen

import android.webkit.CookieManager
import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.LoadingOrDone
import com.vobbla16.mesh.common.ResourceOrNotLoggedIn
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
            when (it) {
                is ResourceOrNotLoggedIn.Success -> {
                    setState { copy(profile = it.data, isLoading = false, error = null) }
                }

                is ResourceOrNotLoggedIn.Loading -> {
                    setState { copy(isLoading = true) }
                }

                is ResourceOrNotLoggedIn.Error -> {
                    setState { copy(error = it.message, isLoading = false) }
                }

                is ResourceOrNotLoggedIn.NotLoggedIn -> {
                    setAction { ProfileScreenAction.NavigateToLoginScreen }
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