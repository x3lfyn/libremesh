package com.vobbla16.mesh.ui.screens.marksScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.use_case.GetMarksReportUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import com.vobbla16.mesh.ui.reduceOtherState
import com.vobbla16.mesh.ui.screens.marksScreen.localState.toSingleDayReports
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
class MarksScreenViewModel(
    private val getMarksReportUseCase: GetMarksReportUseCase
) : BaseViewModel<List<MarksSubjectModel>, MarksScreenState, MarksScreenAction>() {
    override fun setInitialState(): MarksScreenState = MarksScreenState(
        selectedTabIndex = 0,
        openedSubjectsIndices = emptyList(),
        dataGroupedByDate = null
    )

    init {
        getMarksReport(false)
    }

    fun switchTab(selectedTabIndex: Int) {
        setState { reduceOtherState { copy(selectedTabIndex = selectedTabIndex) } }
    }

    fun toggleSubject(tabIndex: Int) {
        if (tabIndex in viewState.value.otherState.openedSubjectsIndices) {
            setState {
                reduceOtherState {
                    copy(
                        openedSubjectsIndices = viewState.value.otherState.openedSubjectsIndices.minus(
                            tabIndex
                        )
                    )
                }
            }
        } else {
            setState {
                reduceOtherState {
                    copy(
                        openedSubjectsIndices = viewState.value.otherState.openedSubjectsIndices.plus(
                            tabIndex
                        )
                    )
                }
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
            onNotLoggedIn = { setAction { MarksScreenAction.NavigateToLoginScreen } })
        setState { reduceOtherState { copy(dataGroupedByDate = viewState.value.data?.toSingleDayReports()) } }
    }

}