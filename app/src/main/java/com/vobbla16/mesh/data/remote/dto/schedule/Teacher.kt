package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    @SerialName("birth_date")
    val birthDate: String?, // null
    @SerialName("first_name")
    val firstName: String?, // Светлана
    @SerialName("last_name")
    val lastName: String?, // Черкасова
    @SerialName("middle_name")
    val middleName: String?, // Викторовна
    @SerialName("sex")
    val sex: String?, // null
    @SerialName("user_id")
    val userId: Int? // null
)