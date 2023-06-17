package com.vobbla16.mesh.data.remote.dto.marks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Period(
    @SerialName("avg_five")
    val avgFive: String,
    @SerialName("avg_hundred")
    val avgHundred: String,
    @SerialName("end")
    val end: String,
    @SerialName("end_iso")
    val endIso: String,
    @SerialName("final_mark")
    val finalMark: String?,
    @SerialName("is_year_mark")
    val isYearMark: Boolean,
    @SerialName("marks")
    val marks: List<Mark>,
    @SerialName("name")
    val name: String,
    @SerialName("start")
    val start: String,
    @SerialName("start_iso")
    val startIso: String
)