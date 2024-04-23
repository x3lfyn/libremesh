package com.vobbla16.mesh.data.remote.dto.subjectMarks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mark(
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("comment_exists")
    val commentExists: Boolean,
    @SerialName("control_form_name")
    val controlFormName: String,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("criteria")
    val criteria: String? = null,
    @SerialName("date")
    val date: String,
    @SerialName("id")
    val id: Long,
    @SerialName("is_exam")
    val isExam: Boolean,
    @SerialName("is_point")
    val isPoint: Boolean,
    @SerialName("original_grade_system_type")
    val originalGradeSystemType: String,
    @SerialName("point_date")
    val pointDate: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("value")
    val value: String,
//    @SerialName("values")
//    val values: Any? = null,
    @SerialName("weight")
    val weight: Int
)