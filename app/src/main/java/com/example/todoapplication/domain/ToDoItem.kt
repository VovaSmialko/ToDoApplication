package com.example.todoapplication.domain

data class ToDoItem(
    val name: String,
    val count: Int,
    val completed: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {

        const val UNDEFINED_ID = -1
    }
}




