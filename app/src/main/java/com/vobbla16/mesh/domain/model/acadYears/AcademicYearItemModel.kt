package com.vobbla16.mesh.domain.model.acadYears

import kotlinx.datetime.LocalDate

data class AcademicYearItemModel(
    val id: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isCurrentYear: Boolean
)
