package com.vobbla16.mesh.data.remote.dto.classmates


import com.vobbla16.mesh.domain.model.classmates.ClassmateModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ClassmateDto(
    @SerialName("agree_pers_data")
    val agreePersData: Boolean,
    @SerialName("class_unit")
    val classUnit: ClassUnit,
    @SerialName("id")
    val id: Int,
    @SerialName("person_id")
    val personId: String,
    @SerialName("roles")
    val roles: List<String?>,
    @SerialName("school")
    val school: School,
    @SerialName("type")
    val type: String,
    @SerialName("user")
    val user: User,
    @SerialName("user_id")
    val userId: Int
) {
    fun toDomain() = ClassmateModel(
        personId = UUID.fromString(personId),
        firstName = user.firstName,
        lastName = user.lastName,
        middleName = user.middleName
    )
}