package com.vobbla16.mesh.ui.screens.subjectScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.domain.use_case.GetMarksForSubjectUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import kotlinx.coroutines.launch

class SubjectScreenViewModel(
    private val marksForSubjectUseCase: GetMarksForSubjectUseCase
) : BaseViewModel<SubjectScreenState, SubjectScreenAction>() {
    override fun setInitialState() = SubjectScreenState(
        marks = GenericHolder(),
        subjectId = null
    )

    fun selectSubject(subjectId: Long) {
        setState { copy(subjectId = subjectId) }
        getMarks(false)
    }

    fun refreshScreen() = getMarks(true)

    private fun getMarks(refresh: Boolean) = viewModelScope.launch {
        viewState.value.subjectId?.let { subjectId ->
            processDataFromUseCase(
                useCase = marksForSubjectUseCase(subjectId),
                loadingType = LoadingState.fromBool(refresh),
                resultReducer = { this },
                newStateApplier = { setState { copy(marks = it) } },
                onNotLoggedIn = { setAction { SubjectScreenAction.NavigateToLoginScreen } }
            )
        }
    }
}