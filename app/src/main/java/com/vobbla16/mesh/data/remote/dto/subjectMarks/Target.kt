package com.vobbla16.mesh.data.remote.dto.subjectMarks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Target(
    @SerialName("paths")
    val paths: List<Path>,
    @SerialName("remain")
    val remain: Int,
    @SerialName("round")
    val round: String? = null,
    @SerialName("value")
    val value: Int
)