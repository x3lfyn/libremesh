package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow

class GetStudentIdUseCase<T>(
    private val meshRepository: MeshRepository
) {
    suspend operator fun invoke(mergeWith: suspend (Int) -> Flow<OrLoading<Resource<T>>>): Flow<OrLoading<Resource<T>>> =
        mergeIfOk(meshRepository.getProfile()) {
            val id = it.children[0].id
            mergeWith(id)
        }
}