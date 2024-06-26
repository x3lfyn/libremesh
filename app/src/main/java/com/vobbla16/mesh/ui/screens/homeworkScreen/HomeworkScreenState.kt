package com.vobbla16.mesh.ui.screens.homeworkScreen

import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateWithLessonModel
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder

data class HomeworkScreenState(
    val homeworkData: GenericHolder<List<HomeworkItemsForDateWithLessonModel>>,
    val loadingDoneIds: List<Long>
) :ViewState
