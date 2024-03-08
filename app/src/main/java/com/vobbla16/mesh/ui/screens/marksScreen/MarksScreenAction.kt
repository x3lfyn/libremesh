package com.vobbla16.mesh.ui.screens.marksScreen

import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.ui.ViewAction

sealed class MarksScreenAction : ViewAction {
    data object NavigateToLoginScreen : MarksScreenAction()
    data object FailedToOpenInfo : MarksScreenAction()
    data class OpenMarkInfo(val lessonSelector: LessonSelector) : MarksScreenAction()
}