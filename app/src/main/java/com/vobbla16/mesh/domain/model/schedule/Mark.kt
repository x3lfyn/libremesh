package com.vobbla16.mesh.domain.model.schedule

import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefaultValue

data class Mark(
    val id: Long,
    val value: String,
    val weight: Int,
    val isPoint: Boolean
) {
    fun toMarkDefaultValue() = MarkDefaultValue(
        value.toInt(),
        weight, isPoint
    )
}