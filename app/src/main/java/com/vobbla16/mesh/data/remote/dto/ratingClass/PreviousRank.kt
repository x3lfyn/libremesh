package com.vobbla16.mesh.data.remote.dto.ratingClass


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviousRank(
    @SerialName("averageMarkFive")
    val averageMarkFive: Double,
    @SerialName("rankPlace")
    val rankPlace: Int
)