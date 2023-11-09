package com.example.todoapplication.data

import com.example.todoapplication.domain.ToDoItem

class ToDoListMapper {

    fun mapEntityToDbModel(toDoItem: ToDoItem): ToDoItemDbModel = ToDoItemDbModel(
        id = toDoItem.id,
        name = toDoItem.name,
        count = toDoItem.count,
        completed = toDoItem.completed
    )

    fun mapDbModelToEntity(toDoItemDbModel: ToDoItemDbModel) = ToDoItem(
        id = toDoItemDbModel.id,
        name = toDoItemDbModel.name,
        count = toDoItemDbModel.count,
        completed = toDoItemDbModel.completed
    )

    fun mapListDbModelToListEntity(list: List<ToDoItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}