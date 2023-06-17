package com.vobbla16.mesh.common

sealed class ResourceOrNotLoggedIn<out T> {
    data class Success<T>(val data: T) : ResourceOrNotLoggedIn<T>()
    data class Error(val message: String) : ResourceOrNotLoggedIn<Nothing>()
    object Loading : ResourceOrNotLoggedIn<Nothing>()
    object NotLoggedIn : ResourceOrNotLoggedIn<Nothing>()
}