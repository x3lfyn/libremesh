package com.vobbla16.mesh.common

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun<reified T, R> HttpResponse.toResource(toDomain: (original: T) -> R): Resource<R> {
    return when(this.status) {
        HttpStatusCode.Unauthorized -> Resource.NotLoggedIn
        else -> {
            try {
                Resource.Ok(toDomain(this.body<T>()))
            } catch (e: Exception) {
                Resource.Err(e)
            }
        }
    }
}