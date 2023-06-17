package com.vobbla16.mesh.data.remote.dto.marks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Value(
    @SerialName("five")
    val five: Double,
    @SerialName("hundred")
    val hundred: Double,
    @SerialName("nmax")
    val nmax: Double,
    @SerialName("original")
    val original: String
)