package com.example.todoapplication.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapplication.domain.ToDoItem
import com.example.todoapplication.domain.ToDoListRepository
import kotlin.random.Random

class ToDoListRepositoryImpl(
    application: Application
) : ToDoListRepository {

    private val toDoListDao = AppDatabase.getInstance(application).toDoListDao()

    private val mapper = ToDoListMapper()

    override suspend fun addToDoItem(toDoItem: ToDoItem) {
        toDoListDao.addToDoItem(mapper.mapEntityToDbModel(toDoItem))
    }

    override suspend fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoListDao.deleteToDoItem(toDoItem.id)
    }

    override suspend fun editTodoItem(toDoItem: ToDoItem) {
        toDoListDao.addToDoItem(mapper.mapEntityToDbModel(toDoItem))
    }

    override suspend fun getToDoItem(toDoItemId: Int): ToDoItem {
        val dbModel = toDoListDao.getToDoItem(toDoItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getToDoList(): LiveData<List<ToDoItem>> = MediatorLiveData<List<ToDoItem>>().apply {
        addSource(toDoListDao.getToDoList()) {
            value = mapper.mapListDbModelToListEntity(it)
        }
    }
}
