package com.vobbla16.mesh.data.remote.dto.shortSchedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    @SerialName("absence_reason_id")
    val absenceReasonId: Long? = null,
    @SerialName("begin_time")
    val beginTime: String,
    @SerialName("bell_id")
    val bellId: Long? = null,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("evaluation")
    val evaluation: String? = null,
    @SerialName("group_id")
    val groupId: Long? = null,
    @SerialName("group_name")
    val groupName: String,
    @SerialName("is_virtual")
    val isVirtual: Boolean,
    @SerialName("lesson_education_type")
    val lessonEducationType: String,
    @SerialName("lesson_id")
    val lessonId: Long? = null,
    @SerialName("lesson_name")
    val lessonName: String?,
    @SerialName("lesson_type")
    val lessonType: String,
    @SerialName("schedule_item_id")
    val scheduleItemId: Long,
    @SerialName("subject_id")
    val subjectId: Long? = null,
    @SerialName("subject_name")
    val subjectName: String? = null
)