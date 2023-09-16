package com.vobbla16.mesh.domain.model.profile

enum class Sex(
    val text: String
) {
    Male("male"),
    Female("female");

    companion object {
        private val mapText = Sex.values().associateBy(Sex::text)

        fun fromString(text: String?) = if(text != null) mapText[text] else null
    }
}