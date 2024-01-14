package com.vobbla16.mesh.domain.model.lessonInfo

import kotlinx.datetime.LocalDateTime

data class LessonInfoModel(
    val id: Long,
    val beginTime: LocalDateTime,
    val endTime: LocalDateTime,
    val subjectId: Long,
    val subjectName: String,
    val teacher: String?,
    val room: String,
    val homeworks: List<Homework>,
    val marks: List<Mark>
) {
    fun durationMinutes(): Int {
        return (endTime.time.toSecondOfDay() - beginTime.time.toSecondOfDay()) / 60
    }
}
