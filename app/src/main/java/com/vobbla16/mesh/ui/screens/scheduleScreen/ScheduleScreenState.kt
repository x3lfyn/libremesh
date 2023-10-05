package com.vobbla16.mesh.ui.screens.scheduleScreen

import com.vobbla16.mesh.domain.model.schedule.LessonType
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import kotlinx.datetime.LocalDate

typealias LessonInfoId = Pair<Long, LessonType>

data class ScheduleScreenState(
    val datePickerOpened: Boolean,
    val selectedDate: LocalDate,
    val showBottomSheet: Boolean,
    val selectedLesson: LessonInfoId?,
    val lessonInfo: GenericHolder<String>
)