package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.DataOrError
import com.vobbla16.mesh.common.ResourceOrNotLoggedIn
import com.vobbla16.mesh.domain.model.profile.Child
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStudentUseCase(
    private val getTokenUseCase: GetTokenUseCase,
    private val getStudentIntUseCase: GetStudentIntUseCase
) {
    operator fun invoke(): Flow<ResourceOrNotLoggedIn<Child>> = flow {
        emit(ResourceOrNotLoggedIn.Loading)

        val token = getTokenUseCase()
        if (token == null) {
            emit(ResourceOrNotLoggedIn.NotLoggedIn)
            return@flow
        }

        when(val student = getStudentIntUseCase(token)) {
            is DataOrError.Success -> {
                emit(ResourceOrNotLoggedIn.Success(student.data))
            }
            is DataOrError.Error -> {
                emit(ResourceOrNotLoggedIn.Error(student.message))
            }
        }
    }
}