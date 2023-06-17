package com.vobbla16.mesh.common

sealed class LoadingOrDone {
    object Loading: LoadingOrDone()
    object Done: LoadingOrDone()
}