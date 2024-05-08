package com.vobbla16.mesh.ui.screens.lessonScreen

import com.vobbla16.mesh.ui.ViewAction

sealed class LessonScreenAction: ViewAction {
    data object NavigateToLoginScreen : LessonScreenAction()
    data class ErrorHomeworkMarkDone(val err: String) : LessonScreenAction()
    data class ErrorHomeworkMarkUndone(val err: String) :  LessonScreenAction()
}