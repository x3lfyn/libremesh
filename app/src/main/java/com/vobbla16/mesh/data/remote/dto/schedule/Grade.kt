package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Grade(
    @SerialName("five")
    val five: Double, // 3.0
    @SerialName("hundred")
    val hundred: Double, // 60.0
    @SerialName("origin")
    val origin: String // 3
)