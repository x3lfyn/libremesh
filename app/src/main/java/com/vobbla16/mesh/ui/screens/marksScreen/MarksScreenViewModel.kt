package com.vobbla16.mesh.ui.screens.marksScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.ResourceOrNotLoggedIn
import com.vobbla16.mesh.common.toText
import com.vobbla16.mesh.domain.use_case.GetMarksReportUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MarksScreenViewModel(
    private val getMarksReportUseCase: GetMarksReportUseCase
) : BaseViewModel<MarksScreenState, MarksScreenAction>() {
    override fun setInitialState(): MarksScreenState = MarksScreenState(
        dataGroupedBySubject = null,
        isLoading = true,
        error = null,
        selectedTabIndex = 0,
        openedSubjectsIndices = emptyList()
    )

    init {
        getMarksReport()
    }

    fun switchTab(selectedTabIndex: Int) {
        setState { copy(selectedTabIndex = selectedTabIndex) }
    }

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

    private fun getMarksReport() = viewModelScope.launch {
        getMarksReportUseCase().onEach {
            when (it) {
                is ResourceOrNotLoggedIn.Success -> {
                    setState { copy(dataGroupedBySubject = it.data, isLoading = false, error = null) }
                }

                is ResourceOrNotLoggedIn.Loading -> {
                    setState { copy(isLoading = true) }
                }

                is ResourceOrNotLoggedIn.Error -> {
                    setState { copy(error = it.e.toText(), isLoading = false) }
                }

                is ResourceOrNotLoggedIn.NotLoggedIn -> {
                    setAction { MarksScreenAction.NavigateToLoginScreen }
                }
            }
        }.collect()
    }
}