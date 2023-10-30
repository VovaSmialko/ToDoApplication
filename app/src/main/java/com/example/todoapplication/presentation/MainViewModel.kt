package com.example.todoapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.domain.DeleteToDoItemUseCase
import com.example.todoapplication.domain.EditToDoItemUseCase
import com.example.todoapplication.domain.GetToDoItemUseCase
import com.example.todoapplication.domain.GetToDoListUseCase
import com.example.todoapplication.domain.ToDoItem

class MainViewModel : ViewModel() {

    private val repository = ToDoListRepositoryImpl

    private val getToDoListUseCase = GetToDoListUseCase(repository)
    private val deleteToDoItemUseCase = DeleteToDoItemUseCase(repository)
    private val editToDoItemUseCase = EditToDoItemUseCase(repository)

    val toDoList = getToDoListUseCase.getToDoList()


    fun deleteToDoItem(toDoItem: ToDoItem) {
        deleteToDoItemUseCase.deleteToDoItem(toDoItem)
    }

    fun changeCompleteState(toDoItem: ToDoItem) {
        val newItem = toDoItem.copy(completed = !toDoItem.completed)
        editToDoItemUseCase.editTodoItem(newItem)
    }
}