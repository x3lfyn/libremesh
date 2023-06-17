package com.vobbla16.mesh.domain.model.homework

import kotlinx.datetime.LocalDate

data class HomeworkItemsForDate(
    val date: LocalDate,
    val items: List<HomeworkItem>
)
