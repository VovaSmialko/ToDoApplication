package com.example.todoapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.domain.ToDoListRepository

object ToDoListRepositoryImpl : ToDoListRepository {
    private val toDoListLD = MutableLiveData<List<ToDoItem>>()

    private val toDoList = mutableListOf<ToDoItem>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = ToDoItem("Name $i", i, true)
            addToDoItem(item)
        }
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        if (toDoItem.id == ToDoItem.UNDEFINED_ID) {
            toDoItem.id = autoIncrementId++
        }
        toDoList.add(toDoItem)
        updateList()
    }

    override fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoList.remove(toDoItem)
        updateList()
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

    override fun getToDoList(): LiveData<List<ToDoItem>> {
        return toDoListLD
    }

    private fun updateList() {
        toDoListLD.value = toDoList.toList()
    }
}
