package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.domain.repository.MeshRepository

class GetClassmatesUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentUseCase: GetStudentUseCase
) {
    suspend operator fun invoke() = mergeIfOk(getStudentUseCase()) {
        meshRepository.getClassmates(it.children[0].classUnitId)
    }
}