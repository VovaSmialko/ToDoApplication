package com.example.todoapplication.domain

class AddToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    suspend fun addToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.addToDoItem(toDoItem)
    }
}