package com.vobbla16.mesh.data.remote.dto.classmates


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassUnit(
    @SerialName("home_based")
    val homeBased: Boolean,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)