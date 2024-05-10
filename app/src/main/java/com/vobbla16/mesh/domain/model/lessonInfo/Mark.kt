package com.vobbla16.mesh.domain.model.lessonInfo

import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefaultValue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Mark(
    val id: Long,
    val value: Int,
    val weight: Int,
    val comment: String?,
    val controlForm: String?,
    val isPoint: Boolean,
    val pointDate: LocalDate?,
    val isExam: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    fun toMarkDefaultValue() = MarkDefaultValue(
        value, weight, isPoint, !comment.isNullOrEmpty()
    )
}
