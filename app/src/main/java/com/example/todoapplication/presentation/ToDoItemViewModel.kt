package com.example.todoapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.data.ToDoListRepositoryImpl
import com.example.todoapplication.domain.AddToDoItemUseCase
import com.example.todoapplication.domain.EditToDoItemUseCase
import com.example.todoapplication.domain.GetToDoItemUseCase
import com.example.todoapplication.domain.ToDoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class ToDoItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)

    private val getToDoItemUseCase = GetToDoItemUseCase(repository)
    private val editToDoItemUseCase = EditToDoItemUseCase(repository)
    private val addToDoItemUseCase = AddToDoItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _toDoItem = MutableLiveData<ToDoItem>()
    val todoItem: LiveData<ToDoItem>
        get() = _toDoItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getToDoItem(toDoItemId: Int) {
        viewModelScope.launch {
            val item = getToDoItemUseCase.getToDoItem(toDoItemId)
            _toDoItem.value = item
        }
    }

    fun getToDoCompletedItem(toDoItemId: Int) {
        viewModelScope.launch {
            val itemCompleted = getToDoItemUseCase.getToDoItem(toDoItemId)
            _toDoItem.value = itemCompleted
        }
    }

    fun addToDoItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val toDoItem = ToDoItem(name, count, true)
                addToDoItemUseCase.addToDoItem(toDoItem)
                finishWork()
            }
        }
    }

    fun editToDoItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _toDoItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editToDoItemUseCase.editTodoItem(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}