package com.vobbla16.mesh.common

sealed class Resource<out T> {
    data class Ok<T>(val data: T) : Resource<T>()
    data class Err(val e: Throwable) : Resource<Nothing>()
    object NotLoggedIn : Resource<Nothing>()
}