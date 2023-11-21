package com.vobbla16.mesh.data.remote.dto.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Children(
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("class_level_id")
    val classLevelId: Long,
    @SerialName("class_name")
    val className: String,
    @SerialName("class_unit_id")
    val classUnitId: Long,
    @SerialName("contingent_guid")
    val contingentGuid: String,
    @SerialName("contract_id")
    val contractId: Long? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("groups")
    val groups: List<Group>,
    @SerialName("id")
    val id: Long,
    @SerialName("is_legal_representative")
    val isLegalRepresentative: Boolean,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
    @SerialName("parallel_curriculum_id")
    val parallelCurriculumId: Long? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("representatives")
    val representatives: List<Representative>,
    @SerialName("school")
    val school: School,
    @SerialName("sections")
    val sections: List<Section>,
    @SerialName("sex")
    val sex: String? = null,
    @SerialName("snils")
    val snils: String? = null,
    @SerialName("sudir_account_exists")
    val sudirAccountExists: Boolean,
    @SerialName("sudir_login")
    val sudirLogin: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("enrollment_date")
    val enrollmentDate: String
)