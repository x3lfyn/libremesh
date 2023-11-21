package com.vobbla16.mesh.data.remote.dto.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Representative(
    @SerialName("birth_date")
    val birthDate: String?,
    @SerialName("contract_id")
    val contractId: Long?, // null
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("id")
    val id: Long,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("sex")
    val sex: String?,
    @SerialName("snils")
    val snils: String,
    @SerialName("type")
    val type: String?,
    @SerialName("user_id")
    val userId: Long?
)