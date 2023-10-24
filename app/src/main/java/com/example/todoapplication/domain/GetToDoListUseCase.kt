package com.example.todoapplication.domain

class GetToDoListUseCase(private val toDoListRepository: ToDoListRepository) {

    fun getToDoList(): List<ToDoItem> {
       return toDoListRepository.getToDoList()
    }
}