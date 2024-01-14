package com.vobbla16.mesh.domain.model.lessonInfo

data class Homework(
    val name: String,
    val isDone: Boolean,
    val additionMaterials: List<AdditionalMaterial>
)
