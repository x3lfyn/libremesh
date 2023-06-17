package com.vobbla16.mesh.data.remote.dto.profile


import com.vobbla16.mesh.domain.model.profile.Child
import com.vobbla16.mesh.domain.model.profile.Group
import com.vobbla16.mesh.domain.model.profile.ProfileModel
import com.vobbla16.mesh.domain.model.profile.School
import com.vobbla16.mesh.domain.model.profile.Sex
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("children")
    val children: List<Children>,
    @SerialName("hash")
    val hash: String,
    @SerialName("profile")
    val profile: ProfileX
)

fun Profile.toDomain() = ProfileModel(
    profile = com.vobbla16.mesh.domain.model.profile.Profile(
        lastName = this.profile.lastName,
        firstName = this.profile.firstName,
        middleName = this.profile.middleName,
        birthDate = this.profile.birthDate.toLocalDate(),
        sex = Sex.fromString(this.profile.sex) ?: Sex.Male,
        userId = this.profile.userId,
        id = this.profile.id,
        contractId = this.profile.contractId,
        phone = this.profile.phone,
        email = this.profile.email,
        snils = this.profile.snils,
        type = this.profile.type
    ),
    children = this.children.map { child ->
        Child(
            lastName = child.lastName,
            firstName = child.firstName,
            middleName = child.middleName,
            birthDate = child.birthDate.toLocalDate(),
            sex = Sex.fromString(child.sex) ?: Sex.Male,
            userId = child.userId,
            id = child.id,
            contractId = child.contractId,
            phone = child.phone,
            email = child.email,
            snils = child.snils,
            type = child.type,
            school = School(
                id = child.school.id,
                name = child.school.name,
                shortName = child.school.shortName,
                county = child.school.county,
                principal = child.school.principal,
                phone = child.school.phone
            ),
            className = child.className,
            classLevelId = child.classLevelId,
            classUnitId = child.classUnitId,
            groups = child.groups.map {grp ->
                Group(
                    id = grp.id,
                    name = grp.name,
                    subjectId = grp.subjectId,
                    isFake = grp.isFake
                )
            },
            representatives = child.representatives.map { repr ->
                com.vobbla16.mesh.domain.model.profile.Profile(
                    lastName = repr.lastName,
                    firstName = repr.firstName,
                    middleName = repr.middleName,
                    birthDate = repr.birthDate?.toLocalDate(),
                    sex = repr.sex?.let { Sex.fromString(it) } ?: Sex.Male,
                    userId = repr.userId,
                    id = repr.id,
                    contractId = repr.contractId,
                    phone = repr.phone,
                    email = repr.email,
                    snils = repr.snils,
                    type = repr.type
                )
            },
            sections = child.sections.map { section ->
                Group(
                    id = section.id,
                    name = section.name,
                    subjectId = section.subjectId,
                    isFake = section.isFake
                )
            },
            sudirAccountExists = child.sudirAccountExists,
            sudirLogin = child.sudirLogin,
            isLegalRepresentative = child.isLegalRepresentative,
            parallelCurriculumId = child.parallelCurriculumId,
            contingentGuid = child.contingentGuid
        )
    },
    hash = this.hash
)