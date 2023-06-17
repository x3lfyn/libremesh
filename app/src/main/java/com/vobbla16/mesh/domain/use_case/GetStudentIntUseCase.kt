package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.Constants
import com.vobbla16.mesh.common.DataOrError
import com.vobbla16.mesh.domain.model.profile.Child
import com.vobbla16.mesh.domain.repository.MeshRepository

class GetStudentIntUseCase(
    private val meshRepository: MeshRepository
) {
    suspend operator fun invoke(token: String): DataOrError<Child> {
        return try {
            val id = meshRepository.getProfile(token).children[0]
            DataOrError.Success(id)
        } catch (e: Exception) {
            DataOrError.Error(e.localizedMessage ?: Constants.DEFAULT_ERROR_MESSAGE)
        }
    }
}