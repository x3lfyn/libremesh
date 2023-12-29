package com.vobbla16.mesh.common.structures

sealed class LoadingOrDone {
    object Loading: LoadingOrDone()
    object Done: LoadingOrDone()
}