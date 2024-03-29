package com.vobbla16.mesh.domain.model.lessonInfo

import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefaultValue
import kotlinx.datetime.LocalDate

data class Mark(
    val value: Int,
    val weight: Int,
    val comment: String?,
    val controlForm: String?,
    val isPoint: Boolean,
    val pointDate: LocalDate?
) {
    fun toMarkDefaultValue() = MarkDefaultValue(
        value, weight, isPoint
    )
}
