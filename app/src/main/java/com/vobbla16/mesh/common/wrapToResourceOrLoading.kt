package com.vobbla16.mesh.common

import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.data.remote.NoTokenException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend inline fun <reified T, R> wrapToResourceOrLoading(
    crossinline toDomain: (original: T) -> R,
    crossinline getData: suspend () -> HttpResponse
): Flow<OrLoading<Resource<R>>> = flow {
    emit(OrLoading.Loading)

    try {
        val response = getData()

        val data = when (response.status) {
            HttpStatusCode.Unauthorized -> Resource.NotLoggedIn
            else -> {
                Resource.Ok(toDomain(response.body<T>()))
            }
        }
        emit(OrLoading.Data(data))
    } catch (e: NoTokenException) {
        emit(OrLoading.Data(Resource.NotLoggedIn))
    } catch (e: Exception) {
        emit(OrLoading.Data(Resource.Err(e)))
    }

}