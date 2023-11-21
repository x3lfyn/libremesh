package com.vobbla16.mesh.domain.model.profile

data class Group(
    val id: Long,
    val name: String,
    val subjectId: Long?,
    val isFake: Boolean
)
