package com.vobbla16.mesh.data.remote.dto.lessonInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonHomework(
    @SerialName("additional_materials")
    val additionalMaterials: List<AdditionalMaterial>,
    @SerialName("date_assigned_on")
    val dateAssignedOn: String,
    @SerialName("date_prepared_for")
    val datePreparedFor: String,
    @SerialName("homework")
    val homework: String,
    @SerialName("homework_created_at")
    val homeworkCreatedAt: String,
    @SerialName("homework_entry_id")
    val homeworkEntryId: Int,
    @SerialName("homework_entry_student_id")
    val homeworkEntryStudentId: Long,
    @SerialName("homework_id")
    val homeworkId: Int,
    @SerialName("homework_updated_at")
    val homeworkUpdatedAt: String,
    @SerialName("is_done")
    val isDone: Boolean,
    @SerialName("materials")
    val materials: List<Material>,
)