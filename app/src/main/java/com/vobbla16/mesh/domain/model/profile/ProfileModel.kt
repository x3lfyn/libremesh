package com.vobbla16.mesh.domain.model.profile

data class ProfileModel(
    val profile: Profile,
    val children: List<Child>,
    val hash: String
)
