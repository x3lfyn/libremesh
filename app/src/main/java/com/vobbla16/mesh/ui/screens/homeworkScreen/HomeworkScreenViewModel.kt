package com.vobbla16.mesh.ui.screens.homeworkScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vobbla16.mesh.common.DataOrErrorOrNotLoggedIn
import com.vobbla16.mesh.common.toText
import com.vobbla16.mesh.domain.use_case.GetHomeworkUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import kotlinx.coroutines.launch

class HomeworkScreenViewModel(
    private val getHomeworkUseCase: GetHomeworkUseCase
) : BaseViewModel<HomeworkScreenState, HomeworkScreenAction>() {
    override fun setInitialState() = HomeworkScreenState(
        pagingFlow = null,
        isLoading = true,
        error = null
    )

    init {
        getHomework()
    }

    private fun getHomework() = viewModelScope.launch {
        setState { copy(isLoading = true) }

        when (val data = getHomeworkUseCase()) {
            is DataOrErrorOrNotLoggedIn.Ok -> {
                setState {
                    copy(
                        pagingFlow = data.data.cachedIn(viewModelScope),
                        isLoading = false,
                        error = null
                    )
                }
            }

            is DataOrErrorOrNotLoggedIn.Err -> {
                setState { copy(isLoading = false, error = data.e.toText()) }
            }

            is DataOrErrorOrNotLoggedIn.NotLoggedIn -> {
                setAction { HomeworkScreenAction.NavigateToLoginScreen }
            }
        }
    }
}