package com.example.todoapplication.domain

class AddToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun addToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.addToDoItem(toDoItem)
    }
}