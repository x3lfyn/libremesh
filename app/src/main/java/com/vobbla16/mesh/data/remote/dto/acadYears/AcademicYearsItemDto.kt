package com.vobbla16.mesh.data.remote.dto.acadYears


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcademicYearsItemDto(
    @SerialName("begin_date")
    val beginDate: String,
    @SerialName("calendar_id")
    val calendarId: Long,
    @SerialName("current_year")
    val currentYear: Boolean,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)