package com.vobbla16.mesh.ui.screens.homeworkScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.domain.use_case.GetHomeworkUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import kotlinx.coroutines.launch

class HomeworkScreenViewModel(
    private val getHomeworkUseCase: GetHomeworkUseCase
) : BaseViewModel<List<HomeworkItemsForDateModel>, Unit, HomeworkScreenAction>() {
    override fun setInitialState() = Unit

    init {
        getHomework(false)
    }

    fun refreshData() = getHomework(true)
    fun retryOnError() = getHomework(false)

    private fun getHomework(refresh: Boolean) = viewModelScope.launch {
        processDataFromUseCase(
            useCase = getHomeworkUseCase(localDateTimeNow().date),
            resultReducer = { this },
            loadingType = LoadingState.fromBool(refresh),
            onNotLoggedIn = { setAction { HomeworkScreenAction.NavigateToLoginScreen } }
        )
    }
}