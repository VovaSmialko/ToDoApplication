package com.example.todoapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.domain.DeleteToDoItemUseCase
import com.example.todoapplication.domain.EditToDoItemUseCase
import com.example.todoapplication.domain.GetToDoItemUseCase
import com.example.todoapplication.domain.GetToDoListUseCase
import com.example.todoapplication.domain.ToDoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)

    private val getToDoListUseCase = GetToDoListUseCase(repository)
    private val deleteToDoItemUseCase = DeleteToDoItemUseCase(repository)
    private val editToDoItemUseCase = EditToDoItemUseCase(repository)

    val toDoList = getToDoListUseCase.getToDoList()
    fun deleteToDoItem(toDoItem: ToDoItem) {
        viewModelScope.launch {
            deleteToDoItemUseCase.deleteToDoItem(toDoItem)
        }

    }

    fun changeCompleteState(toDoItem: ToDoItem) {
        viewModelScope.launch {
            val newItem = toDoItem.copy(completed = !toDoItem.completed)
            editToDoItemUseCase.editTodoItem(newItem)
        }
    }
}