package com.vobbla16.mesh.data.remote.dto.homework


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("file_content_type")
    val fileContentType: String,
    @SerialName("file_file_name")
    val fileFileName: String,
    @SerialName("file_file_size")
    val fileFileSize: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("path")
    val path: String
)