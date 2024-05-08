package com.vobbla16.mesh.ui.screens.lessonScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.Constants
import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.common.toText
import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.domain.model.lessonInfo.Homework
import com.vobbla16.mesh.domain.use_case.GetLessonInfoUseCase
import com.vobbla16.mesh.domain.use_case.MarkHomeworkDoneUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import kotlinx.coroutines.launch

class LessonScreenViewModel(
    private val getLessonInfoUseCase: GetLessonInfoUseCase,
    private val markHomeworkDoneUseCase: MarkHomeworkDoneUseCase
) : BaseViewModel<LessonScreenState, LessonScreenAction>() {
    override fun setInitialState() = LessonScreenState(
        lessonInfo = GenericHolder(),
        selectedLesson = null,
        currentTab = Tabs.Description,
        loadingMarkHomeworkIds = emptyList()
    )

    fun setSelectedLesson(lessonSelector: LessonSelector) {
        setState {
            copy(selectedLesson = lessonSelector)
        }
        getLessonInfo()
    }

    fun refresh() = getLessonInfo(true)
    fun retry() = getLessonInfo(false)

    fun changeTab(newTab: Tabs) = setState { copy(currentTab = newTab) }

    fun toggleDone(homework: Homework) = viewModelScope.launch {
        val newState = !homework.isDone
        markHomeworkDoneUseCase(homework.id, newState).collect {
            when (it) {
                is OrLoading.Loading -> {
                    setState { copy(loadingMarkHomeworkIds = viewState.value.loadingMarkHomeworkIds.plus(homework.id)) }
                }
                is OrLoading.Data -> {
                    setState { copy(loadingMarkHomeworkIds = viewState.value.loadingMarkHomeworkIds.minus(homework.id)) }
                    when (it.res) {
                        is Resource.Err -> {
                            if (newState) {
                                setAction { LessonScreenAction.ErrorHomeworkMarkDone(it.res.e.toText()) }
                            } else {
                                setAction { LessonScreenAction.ErrorHomeworkMarkUndone(it.res.e.toText()) }
                            }
                        }

                        Resource.NotLoggedIn -> {
                            setAction { LessonScreenAction.NavigateToLoginScreen }
                        }

                        is Resource.Ok -> {
                            if (it.res.data.success) {
                                viewState.value.lessonInfo.let { lesson ->
                                    val newHomeworks = lesson.data!!.homeworks.map { hw ->
                                        if (hw.id == homework.id) hw.copy(isDone = newState) else hw
                                    }
                                    setState {
                                        copy(
                                            lessonInfo = lesson.copy(
                                                data = lesson.data.copy(
                                                    homeworks = newHomeworks
                                                )
                                            )
                                        )
                                    }
                                }
                            } else {
                                if (newState) {
                                    setAction { LessonScreenAction.ErrorHomeworkMarkDone(Constants.DEFAULT_ERROR_MESSAGE) }
                                } else {
                                    setAction { LessonScreenAction.ErrorHomeworkMarkUndone(Constants.DEFAULT_ERROR_MESSAGE) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getLessonInfo(refresh: Boolean = false) = viewModelScope.launch {
        viewState.value.selectedLesson?.let { lesson ->
            processDataFromUseCase(
                useCase = getLessonInfoUseCase(lesson),
                resultReducer = { this },
                loadingType = LoadingState.fromBool(refresh),
                onNotLoggedIn = { setAction { LessonScreenAction.NavigateToLoginScreen } },
                newStateApplier = { setState { copy(lessonInfo = it) } }
            )
        }
    }
}