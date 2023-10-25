package com.example.todoapplication.domain

import androidx.lifecycle.LiveData

class GetToDoListUseCase(private val toDoListRepository: ToDoListRepository) {

    fun getToDoList(): LiveData<List<ToDoItem>> {
        return toDoListRepository.getToDoList()
    }
}