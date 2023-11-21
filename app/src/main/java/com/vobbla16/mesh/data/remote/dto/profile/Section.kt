package com.vobbla16.mesh.data.remote.dto.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Section(
    @SerialName("id")
    val id: Long,
    @SerialName("is_fake")
    val isFake: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("subject_id")
    val subjectId: Long?
)