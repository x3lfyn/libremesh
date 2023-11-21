package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Activity {
    abstract val type: String
}

@Serializable
@SerialName("LESSON")
class LessonActivity(
    override val type: String = "LESSON",
    @SerialName("info")
    val info: String?,
    @SerialName("begin_time")
    val beginTime: String,
    @SerialName("begin_utc")
    val beginUtc: Long,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("end_utc")
    val endUtc: Long,
    @SerialName("room_name")
    val roomName: String?,
    @SerialName("room_number")
    val roomNumber: String?,
    @SerialName("building_name")
    val buildingName: String?,
    @SerialName("homework_presence_status_id")
    val homeworkPresenceStatusId: Int?, // 3
    @SerialName("lesson")
    val lesson: Lesson
): Activity()

@Serializable
@SerialName("BREAK")
class BreakActivity(
    override val type: String = "BREAK",
    @SerialName("info")
    val info: String?,
    @SerialName("begin_utc")
    val beginUtc: Long,
    @SerialName("end_utc")
    val endUtc: Long,
    @SerialName("duration")
    val duration: Long
): Activity()