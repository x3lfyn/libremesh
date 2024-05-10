package com.vobbla16.mesh.ui.screens.subjectScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.domain.model.schedule.LessonType
import com.vobbla16.mesh.domain.use_case.GetMarksForSubjectUseCase
import com.vobbla16.mesh.domain.use_case.GetScheduleItemIdFromMarkUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import kotlinx.coroutines.launch

class SubjectScreenViewModel(
    private val marksForSubjectUseCase: GetMarksForSubjectUseCase,
    private val getScheduleItemIdFromMarkUseCase: GetScheduleItemIdFromMarkUseCase
) : BaseViewModel<SubjectScreenState, SubjectScreenAction>() {
    override fun setInitialState() = SubjectScreenState(
        marks = GenericHolder(),
        subjectId = null
    )

    fun selectSubject(subjectId: Long) {
        setState { copy(subjectId = subjectId) }
        getMarks(false)
    }

    fun onRefresh() = getMarks(true)
    fun onRetry() = getMarks(false)

    private fun getMarks(refresh: Boolean) = viewModelScope.launch {
        viewState.value.subjectId?.let { subjectId ->
            processDataFromUseCase(
                useCase = marksForSubjectUseCase(subjectId),
                loadingType = LoadingState.fromBool(refresh),
                resultReducer = { copy(periods = this.periods.reversed()) },
                newStateApplier = { setState { copy(marks = it) } },
                onNotLoggedIn = { setAction { SubjectScreenAction.NavigateToLoginScreen } }
            )
        }
    }

    fun openMarkInfo(markId: Long) = viewModelScope.launch {
        getScheduleItemIdFromMarkUseCase(markId).collect {
            when (it) {
                is OrLoading.Data -> {
                    when (it.res) {
                        is Resource.Ok -> setAction {
                            SubjectScreenAction.OpenMarkInfo(
                                LessonSelector(
                                    it.res.data,
                                    LessonType.OO
                                )
                            )
                        }

                        is Resource.Err -> setAction { SubjectScreenAction.FailedToOpenMark }
                        is Resource.NotLoggedIn -> setAction { SubjectScreenAction.NavigateToLoginScreen }
                    }
                }

                is OrLoading.Loading -> {}
            }
        }
    }
}