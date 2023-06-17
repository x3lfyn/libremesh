package com.vobbla16.mesh.data.remote.dto.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    @SerialName("id")
    val id: Int,
    @SerialName("is_fake")
    val isFake: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("subject_id")
    val subjectId: Int
)