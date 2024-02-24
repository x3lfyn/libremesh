package com.vobbla16.mesh.ui.screens.lessonScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.domain.use_case.GetLessonInfoUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import kotlinx.coroutines.launch

class LessonScreenViewModel(
    private val getLessonInfoUseCase: GetLessonInfoUseCase
) : BaseViewModel<LessonScreenState, LessonScreenAction>() {
    override fun setInitialState() = LessonScreenState(
        lessonInfo = GenericHolder(),
        selectedLesson = null,
        currentTab = Tabs.Description
    )

    fun setSelectedLesson(lessonSelector: LessonSelector) {
        setState {
            copy(selectedLesson = lessonSelector)
        }
        getLessonInfo()
    }

    fun refresh() = getLessonInfo(true)
    fun retry() = getLessonInfo(true)

    fun changeTab(newTab: Tabs) = setState { copy(currentTab = newTab) }

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