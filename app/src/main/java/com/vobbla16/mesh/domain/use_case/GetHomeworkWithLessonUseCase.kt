package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.mapIfOk
import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.common.weekFromFirstDay
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateWithLessonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetHomeworkWithLessonUseCase(
    private val getHomeworkUseCase: GetHomeworkUseCase,
    private val getShortScheduleUseCase: GetShortScheduleUseCase
) {
    suspend operator fun invoke(firstDayOfWeek: LocalDate): Flow<OrLoading<Resource<List<HomeworkItemsForDateWithLessonModel>>>> =
        mergeIfOk(getShortScheduleUseCase(weekFromFirstDay(firstDayOfWeek))) { schedule ->
            getHomeworkUseCase(firstDayOfWeek).mapIfOk { homeworks ->
                homeworks.map { homeworkItemsForDateModel ->
                    HomeworkItemsForDateWithLessonModel(date = homeworkItemsForDateModel.date,
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