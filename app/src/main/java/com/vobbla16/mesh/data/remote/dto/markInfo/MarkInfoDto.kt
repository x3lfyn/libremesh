package com.vobbla16.mesh.data.remote.dto.markInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarkInfoDto(
    @SerialName("activity")
    val activity: Activity
)