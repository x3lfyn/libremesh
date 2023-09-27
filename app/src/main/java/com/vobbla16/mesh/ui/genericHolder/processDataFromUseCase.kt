package com.vobbla16.mesh.ui.genericHolder

import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.ViewAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

suspend fun <R, D, S, A : ViewAction> BaseViewModel<D, S, A>.processDataFromUseCase(
    useCase: Flow<OrLoading<Resource<R>>>,
    resultReducer: R.() -> D,
    loadingType: LoadingState,
    onNotLoggedIn: () -> Unit
) {
    useCase.onEach {
        when (it) {
            is OrLoading.Loading -> {
                when (loadingType) {
                    LoadingState.Load -> {
                        setState {
                            copy(
                                dataState = dataState.copy(
                                    data = null,
                                    loading = LoadingState.Load,
                                    error = null
                                )
                            )
                        }
                    }

                    LoadingState.Refresh -> {
                        setState {
                            copy(
                                dataState = dataState.copy(
                                    data = null,
                                    loading = LoadingState.Refresh,
                                    error = null
                                )
                            )
                        }
                    }

                    else -> {}
                }
            }

            is OrLoading.Data -> {
                when (it.res) {
                    is Resource.Ok -> {
                        setState {
                            copy(
                                dataState = dataState.copy(
                                    data = it.res.data.resultReducer(),
                                    loading = LoadingState.Nothing,
                                    error = null
                                )
                            )
                        }
                    }

                    is Resource.Err -> {
                        setState {
                            copy(
                                dataState = dataState.copy(
                                    data = null,
                                    loading = LoadingState.Nothing,
                                    error = it.res.e
                                )
                            )
                        }
                    }

                    Resource.NotLoggedIn -> {
                        onNotLoggedIn.invoke()
                    }
                }
            }
        }
    }.collect()
}