package com.example.todoapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.domain.DeleteToDoItemUseCase
import com.example.todoapplication.domain.GetToDoListUseCase
import com.example.todoapplication.domain.ToDoItem
import kotlinx.coroutines.launch

class ToDoCompletedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)

    private val getToDoListUseCase = GetToDoListUseCase(repository)
    private val deleteToDoItemUseCase = DeleteToDoItemUseCase(repository)

    val toDoList = getToDoListUseCase.getToDoList()
    fun deleteToDoItemCompleted(toDoItem: ToDoItem) {
        viewModelScope.launch {
            deleteToDoItemUseCase.deleteToDoItem(toDoItem)
        }
    }
}