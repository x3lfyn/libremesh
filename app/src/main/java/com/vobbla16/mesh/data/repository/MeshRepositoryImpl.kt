package com.vobbla16.mesh.data.repository

import com.vobbla16.mesh.common.Constants
import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.common.toStr
import com.vobbla16.mesh.data.remote.MeshApi
import com.vobbla16.mesh.domain.model.acadYears.AcademicYearItemModel
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class MeshRepositoryImpl : MeshRepository {
    companion object {
        private val meshApi = MeshApi()
    }

    override suspend fun getProfile() = meshApi.getProfile()

    override suspend fun getSchedule(studentId: Long, date: LocalDate) =
        meshApi.getSchedule(studentId.toString(), date.toString())

    override suspend fun getAcademicYears() =
        meshApi.getAcademicYears()

    override suspend fun getMarksReport(
        studentId: Long,
        academicYear: AcademicYearItemModel
    ) = meshApi.getMarksReport(studentId.toString(), academicYear.id.toString())

    override suspend fun getHomework(
        studentId: Long,
        week: LocalDate
    ) = meshApi.getHomework(
        studentId,
        week.toStr(),
        (week.plus(DatePeriod(days = 7))).toStr()
    )

    override suspend fun getLessonInfo(
        studentId: Long,
        lessonId: Long
    ): Flow<OrLoading<Resource<LessonInfoModel>>> = meshApi.getLessonInfo(
        studentId,
        lessonId,
        Constants.EDUCATION_TYPE
    )
}
