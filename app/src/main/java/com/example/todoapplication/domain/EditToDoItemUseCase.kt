package com.example.todoapplication.domain

class EditToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    suspend fun editTodoItem(toDoItem: ToDoItem) {
        toDoListRepository.editTodoItem(toDoItem)
    }
}