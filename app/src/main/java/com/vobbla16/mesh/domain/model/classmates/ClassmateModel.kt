package com.vobbla16.mesh.domain.model.classmates

import java.util.*

data class ClassmateModel(
    val personId: UUID,
    val lastName: String,
    val firstName: String,
    val middleName: String
)
