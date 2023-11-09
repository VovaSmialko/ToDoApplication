package com.example.todoapplication.domain

class GetToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    suspend fun getToDoItem(toDoItemId: Int): ToDoItem {
        return toDoListRepository.getToDoItem(toDoItemId)
    }
}