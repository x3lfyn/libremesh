package com.vobbla16.mesh.data.remote.dto.shortSchedule


import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.domain.model.schedule.LessonType
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortScheduleDto(
    @SerialName("payload")
    val payload: List<Payload>
) {
    fun toDomain() = this.payload.groupBy { it.date }.mapKeys { it.key.toLocalDate() }.mapValues {
        it.value.first().lessons.mapNotNull { lesson ->
            lesson.subjectId?.let { subjectId ->
                lesson.lessonId?.let { lessonId ->
                    com.vobbla16.mesh.domain.model.shortSchedule.Lesson(
                        subjectId = subjectId,
                        lessonId = lessonId,
                        lessonSelector = LessonSelector(
                            lesson.scheduleItemId,
                            LessonType.valueOf(lesson.lessonEducationType)
                        )
                    )
                }
            }
        }
    }
}