package com.vobbla16.mesh.domain.model.homework

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class HomeworkItem(
//    val id: Long,
    val isReady: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val description: String,
    val subjectId: Int,
    val subjectName: String,
    val teacherId: Int,
    val dateAssignedOn: LocalDate,
    val datePreparedFor: LocalDate
)
