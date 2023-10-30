package com.example.todoapplication.domain

import androidx.lifecycle.LiveData

interface ToDoListRepository {

    fun addToDoItem(toDoItem: ToDoItem)

    fun deleteToDoItem(toDoItem: ToDoItem)

    fun editTodoItem(toDoItem: ToDoItem)

    fun getToDoItem(toDoItemId: Int): ToDoItem

    fun getToDoList(): LiveData<List<ToDoItem>>

}