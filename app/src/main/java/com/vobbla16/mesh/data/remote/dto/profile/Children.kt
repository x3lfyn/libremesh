package com.vobbla16.mesh.data.remote.dto.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Children(
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("class_level_id")
    val classLevelId: Int,
    @SerialName("class_name")
    val className: String,
    @SerialName("class_unit_id")
    val classUnitId: Int,
    @SerialName("contingent_guid")
    val contingentGuid: String,
    @SerialName("contract_id")
    val contractId: Int? = null,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("groups")
    val groups: List<Group>,
    @SerialName("id")
    val id: Int,
    @SerialName("is_legal_representative")
    val isLegalRepresentative: Boolean,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
    @SerialName("parallel_curriculum_id")
    val parallelCurriculumId: Int?,
    @SerialName("phone")
    val phone: String,
    @SerialName("representatives")
    val representatives: List<Representative>,
    @SerialName("school")
    val school: School,
    @SerialName("sections")
    val sections: List<Section>,
    @SerialName("sex")
    val sex: String,
    @SerialName("snils")
    val snils: String,
    @SerialName("sudir_account_exists")
    val sudirAccountExists: Boolean,
    @SerialName("sudir_login")
    val sudirLogin: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("enrollment_date")
    val enrollmentDate: String
)