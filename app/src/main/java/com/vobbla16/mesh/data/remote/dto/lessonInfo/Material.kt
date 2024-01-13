package com.vobbla16.mesh.data.remote.dto.lessonInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Material(
    @SerialName("action_id")
    val actionId: Int,
    @SerialName("action_name")
    val actionName: String,
    @SerialName("items")
    val items: List<Item>,
    @SerialName("type")
    val type: String,
    @SerialName("type_name")
    val typeName: String
)