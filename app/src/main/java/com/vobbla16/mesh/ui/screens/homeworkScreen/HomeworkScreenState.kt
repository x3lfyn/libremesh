package com.vobbla16.mesh.ui.screens.homeworkScreen

import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder

data class HomeworkScreenState(
    val homeworkData: GenericHolder<List<HomeworkItemsForDateModel>>
) :ViewState
