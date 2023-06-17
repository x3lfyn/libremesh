package com.vobbla16.mesh.data.remote.dto.homework


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    @SerialName("exam_name")
    val examName: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)