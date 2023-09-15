package com.vobbla16.mesh.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

/**
 * Collects events from [first] flow and emits them to output flow, if Resource.Ok got, [second] called with result. All events from second flow proxied to output flow
 */
suspend fun <T, R> mergeIfOk(
    first: Flow<OrLoading<Resource<T>>>,
    second: suspend (T) -> Flow<OrLoading<Resource<R>>>
): Flow<OrLoading<Resource<R>>> = flow {
    first.onEach {
        when (it) {
            is OrLoading.Data -> {
                when (it.res) {
                    is Resource.Ok -> {
                        proxyFrom(second(it.res.data))
                    }

                    is Resource.Err -> emit(OrLoading.Data(Resource.Err(it.res.e)))
                    Resource.NotLoggedIn -> emit(OrLoading.Data(Resource.NotLoggedIn))
                }
            }

            OrLoading.Loading -> emit(OrLoading.Loading)
        }
    }.collect()
}