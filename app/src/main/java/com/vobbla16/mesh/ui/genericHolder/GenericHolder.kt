package com.vobbla16.mesh.ui.genericHolder

data class GenericHolder<out T>(
    val data: T? = null,
    val loading: LoadingState = LoadingState.Nothing,
    val error: Throwable? = null
) {
    val isLoading = loading == LoadingState.Load
    val isRefreshing = loading == LoadingState.Refresh
}