package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.mapIfOk
import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.domain.model.classmates.ClassmateModel
import java.util.UUID

class GetRatingClassDeanonUseCase(
    private val getRatingClassUseCase: GetRatingClassUseCase,
    private val getStudentUseCase: GetStudentUseCase
) {
//    suspend operator fun invoke() =
//        mergeIfOk(getStudentUseCase()) { currentStudent ->
//            getRatingClassUseCase().mapIfOk { rating ->
//                rating.associateBy { it.personId }.entries.associateBy({ it.value }) { it.key }
//                    .map {
//                        Triple(
//                            it.key,
//                            ClassmateModel(
//                                personId = UUID.fromString(""),
//                                firstName = "Unknown",
//                                lastName = "Unknown",
//                                middleName = "Unknown"
//                            ),
//                            currentStudent.children[0].contingentGuid == it.value
//                        )
//                    }.sortedBy { it.first.currentRank.place }
//            }
//        }
    suspend operator fun invoke() =
        mergeIfOk(getStudentUseCase()) { currentStudent ->
            getRatingClassUseCase().mapIfOk { rating ->
                rating.map {
                    Triple(it, ClassmateModel(UUID.randomUUID(), firstName = "Unknown", lastName = "Unknown", middleName = "Unknown"), it.personId == currentStudent.children[0].contingentGuid)
                }
            }
        }
}
