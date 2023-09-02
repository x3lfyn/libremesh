package com.vobbla16.mesh.common

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val e: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}