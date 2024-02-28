package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.mapIfOk
import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.common.toWeek
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDataWithLessonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetHomeworkWithLessonUseCase(
    private val getHomeworkUseCase: GetHomeworkUseCase,
    private val getShortScheduleUseCase: GetShortScheduleUseCase
) {
    suspend operator fun invoke(week: LocalDate): Flow<OrLoading<Resource<List<HomeworkItemsForDataWithLessonModel>>>> =
        mergeIfOk(getShortScheduleUseCase(week.toWeek())) { schedule ->
            getHomeworkUseCase(week).mapIfOk { homeworks ->
                homeworks.map { homeworkItemsForDateModel ->
                    HomeworkItemsForDataWithLessonModel(date = homeworkItemsForDateModel.date,
                        items = homeworkItemsForDateModel.items.map { item ->
                            Pair(
                                item,
                                schedule[homeworkItemsForDateModel.date]
                                    ?.find { it.subjectId == item.subjectId }
                                    ?.lessonSelector
                            )
                        }
                    )
                }
            }
        }

}