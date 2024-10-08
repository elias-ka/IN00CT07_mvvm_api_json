package com.example.mvvmapijson.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmapijson.data.TodosApi
import com.example.mvvmapijson.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TodoViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<TodoUiState> = MutableStateFlow(TodoUiState.Loading)
    val uiState = _uiState.onStart {
        getTodos()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun getTodos() {
        viewModelScope.launch {
            try {
                val todos = TodosApi.retrofitService.getTodos()
                _uiState.value = TodoUiState.Success(todos)
            } catch (_: Exception) {
                _uiState.value = TodoUiState.Error
            }
        }
    }
}

sealed interface TodoUiState {
    data class Success(val todos: List<Todo>) : TodoUiState
    object Loading : TodoUiState
    object Error : TodoUiState
}