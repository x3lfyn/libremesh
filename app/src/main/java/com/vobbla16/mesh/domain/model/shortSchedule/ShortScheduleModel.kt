package com.vobbla16.mesh.domain.model.shortSchedule

import com.vobbla16.mesh.domain.model.common.LessonSelector
import kotlinx.datetime.LocalDate


typealias ShortScheduleModel = Map<LocalDate, List<Lesson>>

data class Lesson(
    val lessonId: Long,
    val subjectId: Long,
    val lessonSelector: LessonSelector
)
