package com.vobbla16.mesh.domain.model.schedule

import kotlinx.datetime.LocalTime
import kotlin.time.Duration

sealed class Activity {
    data class Lesson(
        val info: String,
        val beginTime: LocalTime,
        val endTime: LocalTime,
        val scheduleItemId: Long,
        val subject: String,
        val room: String,
        val teacher: String?,
        val marks: List<Mark>,
        val homework: String,
        val lessonType: LessonType
    ) : Activity()

    data class Break(
        val info: String,
        val beginTime: LocalTime,
        val endTime: LocalTime,
        val duration: Duration
    ) : Activity()
}