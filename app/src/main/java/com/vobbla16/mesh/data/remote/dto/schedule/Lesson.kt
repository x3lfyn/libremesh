package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    @SerialName("absence_reason_id")
    val absenceReasonId: Int?, // null
    @SerialName("ae_fake_group")
    val aeFakeGroup: Boolean? = false, // false
    @SerialName("bell_id")
    val bellId: Int?, // 16104756
    @SerialName("course_lesson_type")
    val courseLessonType: String?, // null
    @SerialName("esz_field_id")
    val eszFieldId: Int?, // 1
    @SerialName("evaluation")
    val evaluation: String?, // null
    @SerialName("homework")
    val homework: String, // не задано
    @SerialName("homework_count")
    val homeworkCount: HomeworkCount,
    @SerialName("is_cancelled")
    val isCancelled: Boolean?, // false
    @SerialName("is_missed_lesson")
    val isMissedLesson: Boolean, // false
    @SerialName("is_virtual")
    val isVirtual: Boolean, // false
    @SerialName("lesson_education_type")
    val lessonEducationType: String, // OO
    @SerialName("lesson_type")
    val lessonType: String, // NORMAL
    @SerialName("link_types")
    val linkTypes: List<String>,
    @SerialName("marks")
    val marks: List<Mark>,
    @SerialName("materials_count")
    val materialsCount: MaterialsCount?,
    @SerialName("replaced")
    val replaced: Boolean, // false
    @SerialName("schedule_item_id")
    val scheduleItemId: Int, // 378977336
    @SerialName("subject_id")
    val subjectId: Int, // 33623666
    @SerialName("subject_name")
    val subjectName: String, // Немецкий язык
    @SerialName("teacher")
    val teacher: Teacher
)