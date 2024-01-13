package com.vobbla16.mesh.data.remote.dto.lessonInfo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class AdditionalMaterial {
    @SerialName("type")
    abstract val type: String

    @Serializable
    @SerialName("test_task_binding")
    data class TestTaskBinding(
        @SerialName("type")
        override val type: String = "test_task_binding",
        @SerialName("title")
        val title: String,
        @SerialName("uuid")
        val uuid: String? = null
    ) : AdditionalMaterial()

    @Serializable
    @SerialName("attachments")
    data class Attachment(
        @SerialName("type")
        override val type: String = "attachments",
        @SerialName("title")
        val title: String,
        @SerialName("urls")
        val links: List<UrlAdditionMaterial>
    ) : AdditionalMaterial()
}