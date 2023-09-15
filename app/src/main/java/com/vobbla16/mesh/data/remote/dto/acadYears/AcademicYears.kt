package com.vobbla16.mesh.data.remote.dto.acadYears


import com.vobbla16.mesh.domain.model.acadYears.AcademicYearItemModel
import kotlinx.datetime.toLocalDate


fun List<AcademicYearsItemDto>.toDomain(): List<AcademicYearItemModel> = this.map { item ->
    AcademicYearItemModel(
        id = item.id,
        startDate = item.beginDate.toLocalDate(),
        endDate = item.endDate.toLocalDate(),
        isCurrentYear = item.currentYear
    )
}