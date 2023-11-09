package com.example.todoapplication.domain

import androidx.lifecycle.LiveData

interface ToDoListRepository {

    suspend fun addToDoItem(toDoItem: ToDoItem)

    suspend fun deleteToDoItem(toDoItem: ToDoItem)

    suspend fun editTodoItem(toDoItem: ToDoItem)

    suspend fun getToDoItem(toDoItemId: Int): ToDoItem

    fun getToDoList(): LiveData<List<ToDoItem>>

}