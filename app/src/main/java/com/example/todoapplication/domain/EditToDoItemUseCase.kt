package com.example.todoapplication.domain

class EditToDoItemUseCase(private val toDoListRepository: ToDoListRepository) {

    fun editTodoItem(toDoItem: ToDoItem) {
        toDoListRepository.editTodoItem(toDoItem)
    }
}