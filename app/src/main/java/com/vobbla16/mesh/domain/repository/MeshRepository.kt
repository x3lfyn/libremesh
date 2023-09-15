package com.vobbla16.mesh.domain.repository

import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.domain.model.acadYears.AcademicYearItemModel
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.model.profile.ProfileModel
import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface MeshRepository {
    suspend fun getProfile(): Flow<OrLoading<Resource<ProfileModel>>>

    suspend fun getSchedule(
        studentId: Int,
        date: LocalDate
    ): Flow<OrLoading<Resource<ScheduleModel>>>

    suspend fun getAcademicYears(): Flow<OrLoading<Resource<List<AcademicYearItemModel>>>>

    suspend fun getMarksReport(
        studentId: Int,
        academicYear: AcademicYearItemModel
    ): Flow<OrLoading<Resource<List<MarksSubjectModel>>>>

    suspend fun getHomework(
        studentId: Int,
        week: LocalDate
    ): Flow<OrLoading<Resource<List<HomeworkItemsForDateModel>>>>
}