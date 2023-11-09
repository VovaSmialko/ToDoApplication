package com.example.todoapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_items")
data class ToDoItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val completed: Boolean
)