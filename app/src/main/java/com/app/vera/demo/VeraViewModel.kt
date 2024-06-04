package com.app.vera.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface Action {
    data class SendMessage(val msg: String, val shouldRestart: Boolean) : Action
}

class VeraViewModel : ViewModel() {

    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action> = _action

    fun sendMessage(msg: String, shouldRestart: Boolean = false) = viewModelScope.launch {
        _action.emit(Action.SendMessage(msg, shouldRestart))
    }
}
