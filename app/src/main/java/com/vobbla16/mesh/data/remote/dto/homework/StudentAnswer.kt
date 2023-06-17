package com.vobbla16.mesh.data.remote.dto.homework


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentAnswer(
    @SerialName("answer")
    val answer: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("updated_at")
    val updatedAt: String
)