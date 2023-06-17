package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.Constants
import com.vobbla16.mesh.common.DataOrError
import com.vobbla16.mesh.common.ResourceOrNotLoggedIn
import com.vobbla16.mesh.domain.model.marks.MarksSubject
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMarksReportUseCase(
    private val meshRepository: MeshRepository,
    private val getStudent: GetStudentIntUseCase,
    private val getTokenUseCase: GetTokenUseCase
) {
    operator fun invoke(): Flow<ResourceOrNotLoggedIn<List<MarksSubject>>> = flow {
        emit(ResourceOrNotLoggedIn.Loading)

        val token = getTokenUseCase()
        if (token == null) {
            emit(ResourceOrNotLoggedIn.NotLoggedIn)
            return@flow
        }

        when (val student = getStudent(token)) {
            is DataOrError.Success -> {
                try {
                    val academicYears = meshRepository.getAcademicYears()
                    val currentYear = academicYears.find { it.isCurrentYear }!!
                    val data = meshRepository.getMarksReport(token, student.data.id, currentYear)
                    emit(ResourceOrNotLoggedIn.Success(data))
                } catch (e: Exception) {
                    emit(
                        ResourceOrNotLoggedIn.Error(
                            e.localizedMessage ?: Constants.DEFAULT_ERROR_MESSAGE
                        )
                    )
                }
            }

            is DataOrError.Error -> {
                emit(ResourceOrNotLoggedIn.Error(student.message))
            }
        }
    }
}