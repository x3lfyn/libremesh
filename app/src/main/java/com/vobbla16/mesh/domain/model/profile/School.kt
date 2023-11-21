package com.vobbla16.mesh.domain.model.profile

data class School(
    val id: Long,
    val name: String,
    val shortName: String,
    val county: String,
    val principal: String,
    val phone: String,
)
