package com.vobbla16.mesh.domain.model.marks

import kotlinx.datetime.LocalDate

data class Mark(
    val comment: String?,
    val controlFormName: String,
    val date: LocalDate,
    val gradeType: GradeType,
    val isPoint: Boolean,
    val pointDate: LocalDate?,
    val topic: String,

    val value: MarkValue,
    val weight: Int
)
