package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeworkCount(
    @SerialName("ready_count")
    val readyCount: Int, // 1
    @SerialName("total_count")
    val totalCount: Int // 1
)