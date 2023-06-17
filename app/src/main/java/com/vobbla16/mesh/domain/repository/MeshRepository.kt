package com.vobbla16.mesh.domain.repository

import androidx.paging.PagingData
import com.vobbla16.mesh.domain.model.acadYears.AcademicYear
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDate
import com.vobbla16.mesh.domain.model.marks.MarksSubject
import com.vobbla16.mesh.domain.model.profile.ProfileModel
import com.vobbla16.mesh.domain.model.schedule.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface MeshRepository {
    suspend fun getProfile(token: String): ProfileModel

    suspend fun getSchedule(token: String, studentId: Int, date: LocalDate): Schedule

    suspend fun getAcademicYears(): List<AcademicYear>

    suspend fun getMarksReport(
        token: String,
        studentId: Int,
        academicYear: AcademicYear
    ): List<MarksSubject>

    suspend fun getHomework(
        token: String,
        studentId: Int
    ): Flow<PagingData<HomeworkItemsForDate>>
}