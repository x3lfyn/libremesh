package com.vobbla16.mesh.data.remote.dto.classmates


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class School(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("short_name")
    val shortName: String
)