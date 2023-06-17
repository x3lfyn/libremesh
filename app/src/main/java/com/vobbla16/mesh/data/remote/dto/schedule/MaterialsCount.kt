package com.vobbla16.mesh.data.remote.dto.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MaterialsCount(
    @SerialName("atomic_object")
    val atomicObject: Int? = null, // 1
    @SerialName("attachments")
    val attachments: Int? = null, // 1
    @SerialName("execute")
    val execute: Int? = null, // 5
    @SerialName("game_app")
    val gameApp: Int? = null, // 1
    @SerialName("test_spec_binding")
    val testSpecBinding: Int? = null, // 2
    @SerialName("workbook")
    val workbook: Int? = null, // 1
    @SerialName("test_task_binding")
    val testTaskBinding: Int? = null, // 1
    @SerialName("learn")
    val learn: Int? = null,
    @SerialName("lesson_template")
    val lessonTemplate: Int? = null
)