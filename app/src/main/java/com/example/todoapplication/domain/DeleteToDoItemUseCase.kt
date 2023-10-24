package com.example.todoapplication.domain

class DeleteToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoListRepository.deleteToDoItem(toDoItem)
    }
}