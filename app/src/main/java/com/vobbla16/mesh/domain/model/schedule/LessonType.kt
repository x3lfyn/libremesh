package com.vobbla16.mesh.domain.model.schedule

enum class LessonType(val str: String) {
    OO("OO"), // Основное Образование(походу)
    AE("AE"), // Additional Education(дополнительное образование) (походу) aka "Дополнительное образование"
    EC("EC"), // Extracurricular Classes(внекласнные занятия) (походу) aka "Внеурочная деятельность"
}