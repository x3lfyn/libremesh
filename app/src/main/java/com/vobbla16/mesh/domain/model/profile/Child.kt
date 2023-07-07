package com.vobbla16.mesh.domain.model.profile

import kotlinx.datetime.LocalDate

data class Child(
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val birthDate: LocalDate,
    val sex: Sex,
    val userId: Int,
    val id: Int,
    val contractId: Int?,
    val phone: String,
    val email: String,
    val snils: String,
    val type: String?,
    val school: School,
    val className: String,
    val classLevelId: Int,
    val classUnitId: Int,
    val groups: List<Group>,
    val representatives: List<Profile>,
    val sections: List<Group>,
    val sudirAccountExists: Boolean,
    val sudirLogin: String?,
    val isLegalRepresentative: Boolean,
    val parallelCurriculumId: Int?,
    val contingentGuid: String
)
