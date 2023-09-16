package com.vobbla16.mesh.data.remote.dto.marks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mark(
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("control_form_id")
    val controlFormId: Int,
    @SerialName("control_form_name")
    val controlFormName: String,
    @SerialName("date")
    val date: String,
    @SerialName("grade_system_type")
    val gradeSystemType: String,
    @SerialName("id")
    val id: Int,
    @SerialName("is_exam")
    val isExam: Boolean,
    @SerialName("is_point")
    val isPoint: Boolean,
    @SerialName("point_date")
    val pointDate: String? = null,
    @SerialName("topic_id")
    val topicId: Int? = null,
    @SerialName("topic_name")
    val topicName: String? = null,
    @SerialName("values")
    val values: List<Value>,
    @SerialName("weight")
    val weight: Int
)