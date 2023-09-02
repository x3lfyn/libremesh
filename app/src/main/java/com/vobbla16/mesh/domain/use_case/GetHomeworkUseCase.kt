package com.vobbla16.mesh.domain.use_case

import androidx.paging.PagingData
import com.vobbla16.mesh.common.DataOrError
import com.vobbla16.mesh.common.DataOrErrorOrNotLoggedIn
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDate
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow

class GetHomeworkUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentIntUseCase: GetStudentIntUseCase,
    private val getTokenUseCase: GetTokenUseCase
) {
    suspend operator fun invoke(): DataOrErrorOrNotLoggedIn<Flow<PagingData<HomeworkItemsForDate>>> {
        val token = getTokenUseCase() ?: return DataOrErrorOrNotLoggedIn.NotLoggedIn

        when (val student = getStudentIntUseCase(token)) {
            is DataOrError.Success -> {
                return try {
                    val data = meshRepository.getHomework(token, student.data.id)
                    DataOrErrorOrNotLoggedIn.Ok(data)
                } catch (e: Exception) {
                    DataOrErrorOrNotLoggedIn.Err(e)
                }
            }

            is DataOrError.Error -> {
                return DataOrErrorOrNotLoggedIn.Err(student.e)
            }
        }
    }
}