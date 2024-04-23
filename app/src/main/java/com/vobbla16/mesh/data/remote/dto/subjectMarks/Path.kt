package com.vobbla16.mesh.data.remote.dto.subjectMarks


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Path(
    @SerialName("remain")
    val remain: Int,
    @SerialName("value")
    val value: Int,
    @SerialName("weight")
    val weight: Int
)