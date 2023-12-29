package com.vobbla16.mesh.ui.screens.profileScreen

import android.webkit.CookieManager
import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.structures.LoadingOrDone
import com.vobbla16.mesh.domain.use_case.GetStudentUseCase
import com.vobbla16.mesh.domain.use_case.LogOutUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val getStudentUseCase: GetStudentUseCase,
    private val logOutUseCase: LogOutUseCase
) : BaseViewModel<ProfileScreenState, ProfileScreenAction>() {
    override fun setInitialState() = ProfileScreenState(
        dialogOpened = false,
        isLoggingOut = false,
        childData = GenericHolder()
    )

    init {
        getProfile()
    }

    fun refreshData() = getProfile(true)
    fun retryOnError() = getProfile(false)

    private fun getProfile(refresh: Boolean = false) = viewModelScope.launch {
        processDataFromUseCase(
            useCase = getStudentUseCase(),
            resultReducer = { this.children[0] },
            loadingType = LoadingState.fromBool(refresh),
            onNotLoggedIn = { setAction { ProfileScreenAction.NavigateToLoginScreen } },
            newStateApplier = { setState { copy(childData = it) } }
        )
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
                    setState {

                        copy(
                            isLoggingOut = false,
                            dialogOpened = false
                        )

                    }
                }
            }
        }.collect()
    }

    fun updatedDialogOpened(isOpened: Boolean) =
        setState { copy(dialogOpened = isOpened) }
}