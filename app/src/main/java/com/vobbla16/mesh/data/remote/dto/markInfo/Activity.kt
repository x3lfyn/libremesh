package com.vobbla16.mesh.data.remote.dto.markInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    @SerialName("lesson_topic")
    val lessonTopic: String,
    @SerialName("schedule_item_id")
    val scheduleItemId: Long
)