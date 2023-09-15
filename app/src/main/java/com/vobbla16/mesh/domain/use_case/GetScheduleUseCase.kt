package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.DataOrError
import com.vobbla16.mesh.common.ResourceOrNotLoggedIn
import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate

class GetScheduleUseCase(
    private val meshRepository: MeshRepository,
    private val getTokenUseCase: GetTokenUseCase,
    private val getStudent: GetStudentIntUseCase
) {
    operator fun invoke(date: LocalDate): Flow<ResourceOrNotLoggedIn<ScheduleModel>> = flow {
        emit(ResourceOrNotLoggedIn.Loading)

        val token = getTokenUseCase()
        if (token == null) {
            emit(ResourceOrNotLoggedIn.NotLoggedIn)
            return@flow
        }

        when (val student = getStudent(token)) {
            is DataOrError.Success -> {
                try {
                    val data = meshRepository.getSchedule(token, student.data.id, date)
                    emit(ResourceOrNotLoggedIn.Success(data))
                } catch (e: Exception) {
                    emit(
                        ResourceOrNotLoggedIn.Error(e)
                    )
                }
            }

            is DataOrError.Error -> {
                emit(ResourceOrNotLoggedIn.Error(student.e))
            }
        }
    }
}