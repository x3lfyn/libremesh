package com.vobbla16.mesh.data.remote.dto.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileX(
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("contract_id")
    val contractId: Int?, // null
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("id")
    val id: Int,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("sex")
    val sex: String,
    @SerialName("snils")
    val snils: String,
    @SerialName("type")
    val type: String,
    @SerialName("user_id")
    val userId: Int
)