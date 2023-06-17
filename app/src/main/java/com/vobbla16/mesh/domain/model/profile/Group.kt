package com.vobbla16.mesh.domain.model.profile

data class Group(
    val id: Int,
    val name: String,
    val subjectId: Int?,
    val isFake: Boolean
)
