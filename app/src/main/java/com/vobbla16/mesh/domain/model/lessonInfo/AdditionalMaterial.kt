package com.vobbla16.mesh.domain.model.lessonInfo

import java.util.UUID

sealed class AdditionalMaterial {
    data class TestTaskBinding(
        val title: String,
        val uuid: UUID?
    ) : AdditionalMaterial()

    data class Attachment(
        val title: String,
        val links: List<String>
    ) : AdditionalMaterial()

    data class GameApp(
        val a: Int
    ): AdditionalMaterial()

    data class AtomicObject(
        val a: Int
    ): AdditionalMaterial()

    data class LessonTemplate(
        val a: Int
    ): AdditionalMaterial()

    data class TestSpecBinding(
        val a: Int
    ): AdditionalMaterial()

    data class Workbook(
        val a: Int
    ): AdditionalMaterial()

    data class FizikonModule(
        val a: Int
    ): AdditionalMaterial()
}