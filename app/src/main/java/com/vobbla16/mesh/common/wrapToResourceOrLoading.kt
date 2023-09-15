package com.vobbla16.mesh.common

import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend inline fun<reified T, R> wrapToResourceOrLoading(crossinline toDomain: (original: T) -> R, crossinline getData: suspend () -> HttpResponse): Flow<OrLoading<Resource<R>>> = flow {
    emit(OrLoading.Loading)

    emit(OrLoading.Data(
        getData().toResource<T, R> { toDomain(it) }
    ))
}