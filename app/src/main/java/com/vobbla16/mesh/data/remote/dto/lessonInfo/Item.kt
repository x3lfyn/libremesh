package com.vobbla16.mesh.data.remote.dto.lessonInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("is_hidden_from_students")
    val isHiddenFromStudents: Boolean,
    @SerialName("is_necessary")
    val isNecessary: Boolean,
    @SerialName("link")
    val link: String? = null,
    @SerialName("selected_mode")
    val selectedMode: String? = null,
    @SerialName("title")
    val title: String,
    @SerialName("urls")
    val urls: List<Url>,
    @SerialName("uuid")
    val uuid: String? = null,
)