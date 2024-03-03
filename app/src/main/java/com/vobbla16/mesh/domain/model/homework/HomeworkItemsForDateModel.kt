package com.vobbla16.mesh.domain.model.homework

import com.vobbla16.mesh.domain.model.common.LessonSelector
import kotlinx.datetime.LocalDate

data class HomeworkItemsForDateModel(
    val date: LocalDate,
    val items: List<HomeworkItem>
)

data class HomeworkItemsForDateWithLessonModel(
    val date: LocalDate,
    val items: List<Pair<HomeworkItem, LessonSelector?>>
)
