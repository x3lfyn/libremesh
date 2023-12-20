package com.vobbla16.mesh.ui.genericHolder

import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
import com.vobbla16.mesh.common.copyDynamic
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.ViewAction
import com.vobbla16.mesh.ui.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KProperty1

suspend fun <UseCaseResult, VMState : ViewState, Data, VMAction : ViewAction> BaseViewModel<VMState, VMAction>.processDataFromUseCase(
    useCase: Flow<OrLoading<Resource<UseCaseResult>>>,
    resultReducer: UseCaseResult.() -> Data,
    loadingType: LoadingState,
    stateProperty: KProperty1<VMState, GenericHolder<Data>>,
    onNotLoggedIn: () -> Unit
) {
    useCase.onEach {
        when (it) {
            is OrLoading.Loading -> {
                when (loadingType) {
                    LoadingState.Load -> {
                        setState {
                            copyDynamic(
                                stateProperty, stateProperty.get(this).copy(
                                    data = null,
                                    loading = LoadingState.Load,
                                    error = null
                                )
                            )
                        }
                    }

                    LoadingState.Refresh -> {
                        setState {
                            copyDynamic(
                                stateProperty, stateProperty.get(this).copy(
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
                            copyDynamic(
                                stateProperty, stateProperty.get(this).copy(
                                    data = it.res.data.resultReducer(),
                                    loading = LoadingState.Nothing,
                                    error = null
                                )
                            )
                        }
                    }

                    is Resource.Err -> {
                        setState {
                            copyDynamic(
                                stateProperty, stateProperty.get(this).copy(
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