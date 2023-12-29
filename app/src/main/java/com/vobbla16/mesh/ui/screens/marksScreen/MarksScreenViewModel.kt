package com.vobbla16.mesh.ui.screens.marksScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.domain.use_case.GetMarksReportUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import com.vobbla16.mesh.ui.screens.marksScreen.localState.toSingleDayReports
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
class MarksScreenViewModel(
    private val getMarksReportUseCase: GetMarksReportUseCase
) : BaseViewModel<MarksScreenState, MarksScreenAction>() {
    override fun setInitialState(): MarksScreenState = MarksScreenState(
        marksData = GenericHolder(),
        selectedTabIndex = 0,
        openedSubjectsIndices = emptyList(),
        dataGroupedByDate = null
    )

    init {
        getMarksReport(false)
    }

//    fun switchTab(selectedTabIndex: Int) {
//        setState { reduceOtherState { copy(selectedTabIndex = selectedTabIndex) } }
//    }

    fun toggleSubject(tabIndex: Int) {
        if (tabIndex in viewState.value.openedSubjectsIndices) {
            setState {
                copy(
                    openedSubjectsIndices = viewState.value.openedSubjectsIndices.minus(
                        tabIndex
                    )
                )

            }
        } else {
            setState {
                copy(
                    openedSubjectsIndices = viewState.value.openedSubjectsIndices.plus(
                        tabIndex
                    )
                )

            }
        }
    }

    fun refreshData() = getMarksReport(true)
    fun retryOnError() = getMarksReport(false)
    private fun getMarksReport(refresh: Boolean = false) = viewModelScope.launch {
        processDataFromUseCase(
            useCase = getMarksReportUseCase(),
            resultReducer = { this },
            loadingType = LoadingState.fromBool(refresh),
            onNotLoggedIn = { setAction { MarksScreenAction.NavigateToLoginScreen } },
            newStateApplier = { setState { copy(marksData = it) } }
        )
        setState { copy(dataGroupedByDate = viewState.value.marksData.data?.toSingleDayReports()) }
    }

}