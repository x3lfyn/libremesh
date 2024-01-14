package com.vobbla16.mesh.data.remote.dto.lessonInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Url(
    @SerialName("url")
    val url: String? = null,
    @SerialName("url_type")
    val urlType: String
)