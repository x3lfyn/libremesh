package com.vobbla16.mesh

import androidx.compose.material3.SnackbarHostState
import com.vobbla16.mesh.ui.BaseViewModel

class MainActivityViewModel: BaseViewModel<MainActivityState, Nothing>() {
    override fun setInitialState() = MainActivityState(
        showBottomBar = false,
        snackbarHostState = SnackbarHostState()
    )

    fun hideBottomBar() = setState { copy(showBottomBar = false) }
    fun showBottomBar() = setState { copy(showBottomBar = true) }
}