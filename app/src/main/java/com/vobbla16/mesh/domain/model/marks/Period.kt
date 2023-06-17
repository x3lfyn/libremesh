package com.vobbla16.mesh.domain.model.marks

import kotlinx.datetime.LocalDate

data class Period(
    val name: String,

    val average: MarkValue,
    val finalMark: Int?,

    val startDate: LocalDate,
    val endDate: LocalDate,

    val isYearMark: Boolean,

    val marks: List<Mark>
)
