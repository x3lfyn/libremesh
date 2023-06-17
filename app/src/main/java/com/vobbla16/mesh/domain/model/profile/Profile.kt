package com.vobbla16.mesh.domain.model.profile

import kotlinx.datetime.LocalDate

data class Profile(
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val birthDate: LocalDate?,
    val sex: Sex,
    val userId: Int?,
    val id: Int,
    val contractId: Int?,
    val phone: String,
    val email: String,
    val snils: String,
    val type: String?,
)
