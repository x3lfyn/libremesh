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

    @Serializable
    @SerialName("game_app")
    data class GameApp(
        @SerialName("type")
        override val type: String = "game_app"
    ): AdditionalMaterial()

    @Serializable
    @SerialName("atomic_object")
    data class AtomicObject(
        @SerialName("type")
        override val type: String = "atomic_object"
    ): AdditionalMaterial()

    @Serializable
    @SerialName("lesson_template")
    data class LessonTemplate(
        @SerialName("type")
        override val type: String = "lesson_template"
    ): AdditionalMaterial()

    @Serializable
    @SerialName("test_spec_binding")
    data class TestSpecBinding(
        @SerialName("type")
        override val type: String = "test_spec_binding"
    ): AdditionalMaterial()

    @Serializable
    @SerialName("workbook")
    data class Workbook(
        @SerialName("type")
        override val type: String = "workbook"
    ): AdditionalMaterial()

    @Serializable
    @SerialName("fizikon_module")
    data class FizikonModule(
        @SerialName("type")
        override val type: String = "fizikon_module"
    ): AdditionalMaterial()
}