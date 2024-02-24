package com.vobbla16.mesh.ui.screens.lessonScreen

import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder

data class LessonScreenState(
    val lessonInfo: GenericHolder<LessonInfoModel>,
    val selectedLesson: LessonSelector?,
    val currentTab: Tabs
): ViewState
