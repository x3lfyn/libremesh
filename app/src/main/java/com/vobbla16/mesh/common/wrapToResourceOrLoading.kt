package com.vobbla16.mesh.common

import com.vobbla16.mesh.data.remote.NoTokenException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend inline fun <reified T, R> wrapToResourceOrLoading(
    crossinline toDomain: (original: T) -> R,
    crossinline getData: suspend () -> HttpResponse
): Flow<OrLoading<Resource<R>>> = flow {
    emit(OrLoading.Loading)

    try {
        emit(OrLoading.Data(
            getData().toResource<T, R> { toDomain(it) }
        ))
    } catch (e: NoTokenException) {
        emit(OrLoading.Data(Resource.NotLoggedIn))
    }

}