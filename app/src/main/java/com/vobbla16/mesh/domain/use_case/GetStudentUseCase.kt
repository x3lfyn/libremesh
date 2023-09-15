package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.repository.MeshRepository

class GetStudentUseCase(
    private val meshRepository: MeshRepository
) {
    suspend operator fun invoke() = meshRepository.getProfile()
}