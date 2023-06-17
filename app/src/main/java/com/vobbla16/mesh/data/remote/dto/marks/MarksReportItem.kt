package com.vobbla16.mesh.data.remote.dto.marks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarksReportItem(
    @SerialName("attestation_mark")
    val attestationMark: String? = null,
    @SerialName("avg_five")
    val avgFive: String,
    @SerialName("avg_hundred")
    val avgHundred: String,
    @SerialName("exam_mark")
    val examMark: String? = null,
    @SerialName("periods")
    val periods: List<Period>,
    @SerialName("subject_name")
    val subjectName: String,
    @SerialName("year_mark")
    val yearMark: String?
)