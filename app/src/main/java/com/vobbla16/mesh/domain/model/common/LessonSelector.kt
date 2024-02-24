package com.vobbla16.mesh.domain.model.common

import com.vobbla16.mesh.domain.model.schedule.LessonType
import kotlinx.serialization.Serializable

@Serializable
data class LessonSelector(
    val scheduleItemId: Long,
    val lessonType: LessonType
)
