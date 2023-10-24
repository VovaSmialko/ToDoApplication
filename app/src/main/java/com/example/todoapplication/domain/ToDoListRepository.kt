package com.example.todoapplication.domain

interface ToDoListRepository {

    fun addToDoItem(toDoItem: ToDoItem)

    fun deleteToDoItem(toDoItem: ToDoItem)

    fun editTodoItem(toDoItem: ToDoItem)

    fun getToDoItem(toDoItemId: Int): ToDoItem

    fun getToDoList(): List<ToDoItem>

}