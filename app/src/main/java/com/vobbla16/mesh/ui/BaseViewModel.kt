package com.vobbla16.mesh.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewState

interface ViewAction

abstract class BaseViewModel<UiState: ViewState, Action: ViewAction>: ViewModel() {
    abstract fun setInitialState(): UiState

    private val initialState: UiState by lazy { setInitialState() }

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _action: Channel<Action> = Channel()
    val action = _action.receiveAsFlow()


    fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setAction(builder: () -> Action) {
        val actionValue = builder()
        viewModelScope.launch { _action.send(actionValue) }
    }
}
