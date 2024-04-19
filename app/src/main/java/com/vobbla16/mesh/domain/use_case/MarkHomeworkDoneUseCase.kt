package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.domain.model.homeworkDone.HomeworkDoneModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow

class MarkHomeworkDoneUseCase(
    private val meshRepository: MeshRepository
) {
    suspend operator fun invoke(homeworkId: Long, done: Boolean): Flow<OrLoading<Resource<HomeworkDoneModel>>> {
        return if(done) {
            meshRepository.markHomeworkDone(homeworkId)
        } else {
            meshRepository.unmarkHomeworkDone(homeworkId)
        }
    }
}