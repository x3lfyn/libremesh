package com.vobbla16.mesh.ui.screens.homeworkScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.toText
import com.vobbla16.mesh.domain.use_case.GetHomeworkUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.screens.profileScreen.ProfileScreenAction
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeworkScreenViewModel(
    private val getHomeworkUseCase: GetHomeworkUseCase
) : BaseViewModel<HomeworkScreenState, HomeworkScreenAction>() {
    override fun setInitialState() = HomeworkScreenState(
        data = null,
        isLoading = true,
        error = null
    )

    init {
        getHomework()
    }
    private fun getHomework() = viewModelScope.launch {
        getHomeworkUseCase(localDateTimeNow().date).onEach {
            when(it) {
                is OrLoading.Loading -> {
                    setState { copy(isLoading = true) }
                }
                is OrLoading.Data -> {
                    when(it.res) {
                        is Resource.Ok -> {
                            setState { copy(data = it.res.data, isLoading = false, error = null) }
                        }
                        is Resource.Err -> {
                            setState { copy(error = it.res.e.toText(), isLoading = false) }
                        }
                        is Resource.NotLoggedIn -> {
                            setAction { HomeworkScreenAction.NavigateToLoginScreen }
                        }
                    }
                }
            }
        }.collect()
    }
}