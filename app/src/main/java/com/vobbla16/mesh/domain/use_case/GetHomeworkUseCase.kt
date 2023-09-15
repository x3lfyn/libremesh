package com.vobbla16.mesh.domain.use_case

import androidx.paging.PagingData
import com.vobbla16.mesh.common.DataOrError
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow

class GetHomeworkUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentIntUseCase: GetStudentIntUseCase,
    private val getTokenUseCase: GetTokenUseCase
) {
    suspend operator fun invoke(): Resource<Flow<PagingData<HomeworkItemsForDateModel>>> {
        val token = getTokenUseCase() ?: return Resource.NotLoggedIn

        when (val student = getStudentIntUseCase(token)) {
            is DataOrError.Success -> {
                return try {
                    val data = meshRepository.getHomework(token, student.data.id)
                    Resource.Ok(data)
                } catch (e: Exception) {
                    Resource.Err(e)
                }
            }

            is DataOrError.Error -> {
                return Resource.Err(student.e)
            }
        }
    }
}