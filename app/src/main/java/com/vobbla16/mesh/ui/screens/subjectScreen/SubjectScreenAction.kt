package com.vobbla16.mesh.ui.screens.subjectScreen

import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.ui.ViewAction

sealed class SubjectScreenAction : ViewAction {
    data object NavigateToLoginScreen : SubjectScreenAction()
    data object FailedToOpenMark : SubjectScreenAction()
    data class OpenMarkInfo(val selector: LessonSelector) : SubjectScreenAction()
}