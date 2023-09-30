package com.vobbla16.mesh.ui.genericHolder

enum class LoadingState {
    Load,
    Refresh,
    Nothing;

    companion object {
        fun fromBool(refresh: Boolean) = if (refresh) Refresh else Load
    }
}