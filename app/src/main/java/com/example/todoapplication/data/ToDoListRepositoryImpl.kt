package com.example.todoapplication.data

import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.domain.ToDoListRepository

object ToDoListRepositoryImpl : ToDoListRepository {

    private val toDoList = mutableListOf<ToDoItem>()

    private var autoIncrementId = 0

    override fun addToDoItem(toDoItem: ToDoItem) {
        if (toDoItem.id == ToDoItem.UNDEFINED_ID) {
            toDoItem.id = autoIncrementId++
        }
        toDoList.add(toDoItem)
    }

    override fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoList.remove(toDoItem)
    }

    override fun editTodoItem(toDoItem: ToDoItem) {
        val oldElement = getToDoItem(toDoItem.id)
        toDoList.remove(oldElement)
        addToDoItem(toDoItem)
    }

    override fun getToDoItem(toDoItemId: Int): ToDoItem {
        return toDoList.find {
            it.id == toDoItemId
        } ?: throw RuntimeException("Element with $toDoItemId not found")
    }

    override fun getToDoList(): List<ToDoItem> {
        return toDoList.toList()
    }
}
