package com.vobbla16.mesh.domain.model.acadYears

import kotlinx.datetime.LocalDate

data class AcademicYear(
    val id: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isCurrentYear: Boolean
)
