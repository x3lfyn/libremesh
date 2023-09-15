package com.vobbla16.mesh.ui.screens.marksScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
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
                is OrLoading.Loading -> {
                    setState { copy(isLoading = true) }
                }

                is OrLoading.Data -> {
                    when (it.res) {
                        is Resource.Ok -> {
                            setState {
                                copy(
                                    dataGroupedBySubject = it.res.data,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }

                        is Resource.Err -> {
                            setState { copy(error = it.res.e.toText(), isLoading = false) }
                        }

                        is Resource.NotLoggedIn -> {
                            setAction { MarksScreenAction.NavigateToLoginScreen }
                        }
                    }
                }
            }
        }.collect()
    }
}