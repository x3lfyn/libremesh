package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Value(
    @SerialName("grade")
    val grade: Grade,
    @SerialName("grade_system_id")
    val gradeSystemId: Long, // 4180172
    @SerialName("grade_system_type")
    val gradeSystemType: String, // five
    @SerialName("name")
    val name: String, // 5-балльная
    @SerialName("nmax")
    val nmax: Double // 6.0
)