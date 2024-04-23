package com.vobbla16.mesh.data.remote.dto.subjectMarks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Period(
    @SerialName("count")
    val count: Int,
    @SerialName("dynamic")
    val `dynamic`: String,
    @SerialName("end")
    val end: String,
    @SerialName("end_iso")
    val endIso: String,
    @SerialName("fixed_value")
    val fixedValue: String? = null,
    @SerialName("marks")
    val marks: List<Mark>,
    @SerialName("start")
    val start: String,
    @SerialName("start_iso")
    val startIso: String,
    @SerialName("target")
    val target: Target? = null,
    @SerialName("title")
    val title: String,
    @SerialName("value")
    val value: String
)