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
}