package com.example.todoapplication.domain

class GetToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun getToDoItem(toDoItemId: Int): ToDoItem {
        return toDoListRepository.getToDoItem(toDoItemId)
    }
}