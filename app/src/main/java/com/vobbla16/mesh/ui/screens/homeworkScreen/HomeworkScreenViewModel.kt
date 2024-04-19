package com.vobbla16.mesh.ui.screens.homeworkScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.domain.model.homework.HomeworkItem
import com.vobbla16.mesh.domain.use_case.GetHomeworkWithLessonUseCase
import com.vobbla16.mesh.domain.use_case.MarkHomeworkDoneUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import kotlinx.coroutines.launch

class HomeworkScreenViewModel(
    private val getHomeworkWithLessonUseCase: GetHomeworkWithLessonUseCase,
    private val markHomeworkDoneUseCase: MarkHomeworkDoneUseCase
) : BaseViewModel<HomeworkScreenState, HomeworkScreenAction>() {
    override fun setInitialState() = HomeworkScreenState(
        homeworkData = GenericHolder(),
        loadingDoneIds = emptyList()
    )

    init {
        getHomework(false)
    }

    fun refreshData() = getHomework(true)
    fun retryOnError() = getHomework(false)

    fun toggleDone(homeworkItem: HomeworkItem) = viewModelScope.launch {
        val newState = !homeworkItem.isReady

        markHomeworkDoneUseCase(homeworkItem.id, newState).collect {
            when(it) {
                is OrLoading.Loading -> {
                    setState { copy(loadingDoneIds = viewState.value.loadingDoneIds.plus(homeworkItem.id)) }
                }
                is OrLoading.Data -> {
                    setState { copy(loadingDoneIds = viewState.value.loadingDoneIds.minus(homeworkItem.id)) }
                    when(it.res) {
                        is Resource.Ok -> {
                            if (it.res.data.success) {
                                viewModelScope.launch { getHomework(true) }
                            } else {
                                setAction {
                                    HomeworkScreenAction.ShowMarkDoneError(Error("Unknown error"))
                                }
                            }
                        }
                        is Resource.Err -> {
                            setAction {
                                HomeworkScreenAction.ShowMarkDoneError(it.res.e)
                            }
                        }
                        Resource.NotLoggedIn -> {
                            setAction {
                                HomeworkScreenAction.ShowMarkDoneError(Error("Not logged in"))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getHomework(refresh: Boolean) = viewModelScope.launch {
        processDataFromUseCase(
            useCase = getHomeworkWithLessonUseCase(localDateTimeNow().date),
            resultReducer = { this },
            loadingType = LoadingState.fromBool(refresh),
            onNotLoggedIn = { setAction { HomeworkScreenAction.NavigateToLoginScreen } },
            newStateApplier = { setState { copy(homeworkData = it) } }
        )
    }
}