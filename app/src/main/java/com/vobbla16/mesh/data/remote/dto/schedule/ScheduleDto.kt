package com.vobbla16.mesh.data.remote.dto.schedule


import com.vobbla16.mesh.common.Constants
import com.vobbla16.mesh.common.orDefault
import com.vobbla16.mesh.common.secsToLocalDateTime
import com.vobbla16.mesh.domain.model.schedule.LessonType
import com.vobbla16.mesh.domain.model.schedule.Mark
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Serializable
data class ScheduleDto(
    @SerialName("activities")
    val activities: List<Activity>,
    @SerialName("date")
    val date: String, // 2023-05-03
    @SerialName("has_homework")
    val hasHomework: Boolean, // true
    @SerialName("summary")
    val summary: String // 11 уроков
)

fun ScheduleDto.toDomain(): com.vobbla16.mesh.domain.model.schedule.ScheduleModel {
    return com.vobbla16.mesh.domain.model.schedule.ScheduleModel(
        date = this.date.toLocalDate(),
        summary = this.summary,
        activities = this.activities.map { act ->
            return@map when (act) {
                is LessonActivity -> {
                    com.vobbla16.mesh.domain.model.schedule.Activity.Lesson(
                        isMissed = act.lesson.isMissedLesson,
                        info = act.info.orDefault(),
                        beginTime = act.beginTime.toLocalTime(),
                        endTime = act.endTime.toLocalTime(),
                        scheduleItemId = act.lesson.scheduleItemId,
                        subject = act.lesson.subjectName,
                        room = "${act.buildingName.orDefault()}, ${act.roomNumber.orDefault()}",
                        teacher = act.lesson.teacher.toStringOrNull() ,
                        marks = act.lesson.marks.map { mark ->
                            Mark(
                                mark.id,
                                mark.value,
                                mark.weight,
                                mark.isPoint
                            )
                        },
                        homework = act.lesson.homework,
                        lessonType = when(act.lesson.lessonEducationType) {
                            "OO" -> LessonType.OO
                            "AE" -> LessonType.AE
                            "EC" -> LessonType.EC
                            else -> LessonType.OO
                        }
                    )
                }

                is BreakActivity -> {
                    com.vobbla16.mesh.domain.model.schedule.Activity.Break(
                        info = act.info ?: Constants.DEFAULT_STRING,
                        beginTime = secsToLocalDateTime(act.beginUtc).time,
                        endTime = secsToLocalDateTime(act.endUtc).time,
                        duration = act.duration.toDuration(DurationUnit.SECONDS)
                    )
                }
            }
        }
    )
}

fun Teacher.toStringOrNull(): String? {
    return if (this.lastName.isNullOrEmpty() && this.firstName.isNullOrEmpty() && this.middleName.isNullOrEmpty()) {
        null
    }
    else {
        "${this.lastName.orEmpty()} ${this.firstName.orEmpty()} ${this.middleName.orEmpty()}"
    }
}