package com.vobbla16.mesh.ui.genericHolder

import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

suspend fun <UseCaseResult, Data> processDataFromUseCase(
    useCase: Flow<OrLoading<Resource<UseCaseResult>>>,
    resultReducer: UseCaseResult.() -> Data,
    loadingType: LoadingState,
    onNotLoggedIn: () -> Unit,
    newStateApplier: (GenericHolder<Data>) -> Unit
) {
    useCase.onEach {
        when (it) {
            is OrLoading.Loading -> {
                when (loadingType) {
                    LoadingState.Load -> {
                        newStateApplier(
                            GenericHolder<Data>(
                                data = null,
                                loading = LoadingState.Load,
                                error = null
                            )
                        )
                    }

                    LoadingState.Refresh -> {
                        newStateApplier(
                            GenericHolder<Data>(
                                data = null,
                                loading = LoadingState.Refresh,
                                error = null
                            )
                        )
                    }

                    else -> {}
                }
            }

            is OrLoading.Data -> {
                when (it.res) {
                    is Resource.Ok -> {
                        newStateApplier(
                            GenericHolder<Data>(
                                data = it.res.data.resultReducer(),
                                loading = LoadingState.Nothing,
                                error = null
                            )
                        )
                    }

                    is Resource.Err -> {
                        newStateApplier(
                            GenericHolder<Data>(
                                data = null,
                                loading = LoadingState.Nothing,
                                error = it.res.e
                            )
                        )
                    }

                    Resource.NotLoggedIn -> {
                        onNotLoggedIn.invoke()
                    }
                }
            }
        }
    }.collect()
}