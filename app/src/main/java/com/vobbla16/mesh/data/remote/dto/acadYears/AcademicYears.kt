package com.vobbla16.mesh.data.remote.dto.acadYears


import com.vobbla16.mesh.domain.model.acadYears.AcademicYear
import kotlinx.datetime.toLocalDate


fun List<AcademicYearsItem>.toDomain(): List<AcademicYear> = this.map { item ->
    AcademicYear(
        id = item.id,
        startDate = item.beginDate.toLocalDate(),
        endDate = item.endDate.toLocalDate(),
        isCurrentYear = item.currentYear
    )
}