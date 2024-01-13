package com.vobbla16.mesh.data.remote.dto.lessonInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UrlAdditionMaterial(
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String
)