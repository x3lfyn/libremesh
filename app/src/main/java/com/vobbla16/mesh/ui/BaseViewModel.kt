package com.vobbla16.mesh.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class ViewState<T, O>(
    val dataState: GenericHolder<T> = GenericHolder(),
    val otherState: O
) {
    val data = dataState.data
}

fun <D, O> ViewState<D, O>.reduceOtherState(reducer: O.() -> O): ViewState<D, O> {
    return copy(otherState = otherState.reducer())
}

interface ViewAction

abstract class BaseViewModel<Data, OtherState, Action : ViewAction> : ViewModel() {
    abstract fun setInitialState(): OtherState

    private val initialState: ViewState<Data, OtherState> by lazy { ViewState(otherState = setInitialState()) }

    private val _viewState: MutableState<ViewState<Data, OtherState>> = mutableStateOf(initialState)
    val viewState: State<ViewState<Data, OtherState>> = _viewState

    private val _action: Channel<Action> = Channel()
    val action = _action.receiveAsFlow()


    fun setState(reducer: ViewState<Data, OtherState>.() -> ViewState<Data, OtherState>) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setAction(builder: () -> Action) {
        val actionValue = builder()
        viewModelScope.launch { _action.send(actionValue) }
    }
}
