package com.example.todoapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoListDao {

    @Query("SELECT * FROM todo_items")
    fun getToDoList(): LiveData<List<ToDoItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDoItem(toDoItemDbModel: ToDoItemDbModel)

    @Query("DELETE FROM todo_items WHERE id=:todoItemId")
    suspend fun deleteToDoItem(todoItemId: Int)

    @Query("SELECT * FROM todo_items WHERE id=:todoItemId LIMIT 1")
    suspend fun getToDoItem(todoItemId: Int): ToDoItemDbModel
}