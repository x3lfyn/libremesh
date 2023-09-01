package com.vobbla16.mesh.data.remote.dto.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class School(
    @SerialName("county")
    val county: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("principal")
    val principal: String,
    @SerialName("short_name")
    val shortName: String,
    @SerialName("global_school_id")
    val globalSchoolId: Int
)