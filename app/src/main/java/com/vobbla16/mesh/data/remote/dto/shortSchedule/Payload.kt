package com.vobbla16.mesh.data.remote.dto.shortSchedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    @SerialName("date")
    val date: String,
    @SerialName("lessons")
    val lessons: List<Lesson>
)