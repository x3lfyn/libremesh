package com.vobbla16.mesh.domain.model.marks

import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefaultValue
import kotlinx.datetime.LocalDate

data class Mark(
    val comment: String?,
    val controlFormName: String?,
    val date: LocalDate,
    val gradeType: GradeType,
    val isPoint: Boolean,
    val pointDate: LocalDate?,
    val topic: String,

    val value: MarkValue,
    val weight: Int
) {
    fun toMarkDefaultValue() = MarkDefaultValue(
        value = value.fiveScale.toInt(),
        weight = weight,
        isPoint = isPoint
    )
}