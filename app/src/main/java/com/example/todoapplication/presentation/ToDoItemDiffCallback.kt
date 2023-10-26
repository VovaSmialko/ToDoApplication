package com.example.todoapplication.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapplication.domain.ToDoItem

class ToDoItemDiffCallback : DiffUtil.ItemCallback<ToDoItem>() {

    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }
}