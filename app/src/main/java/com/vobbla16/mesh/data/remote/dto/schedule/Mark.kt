package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mark(
    @SerialName("comment")
    val comment: String, // графики
    @SerialName("comment_exists")
    val commentExists: Boolean, // true
    @SerialName("control_form_name")
    val controlFormName: String?, // null
    @SerialName("created_at")
    val createdAt: String, // 2023-05-18T01:50:00
    @SerialName("criteria")
    val criteria: List<String>,
    @SerialName("id")
    val id: Long, // 2061863223
    @SerialName("is_exam")
    val isExam: Boolean, // false
    @SerialName("is_point")
    val isPoint: Boolean, // false
    @SerialName("original_grade_system_type")
    val originalGradeSystemType: String, // five
    @SerialName("point_date")
    val pointDate: String?, // 2023-05-19
    @SerialName("updated_at")
    val updatedAt: String, // 2023-05-18T01:50:00
    @SerialName("value")
    val value: String, // 3
    @SerialName("values")
    val values: List<Value>,
    @SerialName("weight")
    val weight: Int // 2
)