package com.vobbla16.mesh.data.remote.dto.homework


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Homework(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("date_assigned_on")
    val dateAssignedOn: String,
    @SerialName("date_prepared_for")
    val datePreparedFor: String,
    @SerialName("deleted_at")
    val deletedAt: String?,
    @SerialName("deleted_by")
    val deletedBy: String?,
    @SerialName("group_id")
    val groupId: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("is_required")
    val isRequired: Boolean?,
    @SerialName("lesson_prepared_for")
    val lessonPreparedFor: String?,
    @SerialName("mark_required")
    val markRequired: Boolean?,
    @SerialName("subject")
    val subject: Subject,
    @SerialName("subject_id")
    val subjectId: Int,
    @SerialName("teacher_id")
    val teacherId: Int,
    @SerialName("updated_at")
    val updatedAt: String
)