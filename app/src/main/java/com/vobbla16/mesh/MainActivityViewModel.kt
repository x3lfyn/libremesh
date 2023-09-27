package com.vobbla16.mesh

import androidx.compose.material3.SnackbarHostState
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.reduceOtherState

class MainActivityViewModel : BaseViewModel<Nothing, MainActivityState, Nothing>() {
    override fun setInitialState() = MainActivityState(
        showBottomBar = false,
        snackbarHostState = SnackbarHostState()
    )

    fun hideBottomBar() = setState { reduceOtherState { copy(showBottomBar = false) } }
    fun showBottomBar() = setState { reduceOtherState { copy(showBottomBar = true) } }
}