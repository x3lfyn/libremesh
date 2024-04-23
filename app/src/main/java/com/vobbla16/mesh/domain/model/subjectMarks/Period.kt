package com.vobbla16.mesh.domain.model.subjectMarks

import kotlinx.datetime.LocalDate

data class Period(
    val title: String,
    val average: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val fixedMark: Int?,
    val marks: List<Mark>
)
