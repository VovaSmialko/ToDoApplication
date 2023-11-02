package com.example.todoapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ItemTodoDisabledBinding
import com.example.todoapplication.databinding.ItemTodoEnabledBinding
import com.example.todoapplication.domain.ToDoItem

class ToDoListAdapter :
    ListAdapter<ToDoItem, ToDoItemViewHolder>(ToDoItemDiffCallback()) {

    var onToDOItemLongClickListener: ((ToDoItem) -> Unit)? = null
    var onToDOItemClickListener: ((ToDoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_COMPLETED -> R.layout.item_todo_disabled
            VIEW_TYPE_ACTIVE -> R.layout.item_todo_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ToDoItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ToDoItemViewHolder, position: Int) {
        val toDoItem = getItem(position)
        val binding = viewHolder.binding
        binding.root.setOnLongClickListener {
            onToDOItemLongClickListener?.invoke(toDoItem)
            true
        }
        binding.root.setOnClickListener {
            onToDOItemClickListener?.invoke(toDoItem)
        }
        when (binding) {
            is ItemTodoDisabledBinding -> {
                binding.toDoItem = toDoItem
            }

            is ItemTodoEnabledBinding -> {
                binding.toDoItem = toDoItem
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.completed) {
            VIEW_TYPE_ACTIVE
        } else {
            VIEW_TYPE_COMPLETED
        }
    }


    companion object {

        const val VIEW_TYPE_COMPLETED = 100
        const val VIEW_TYPE_ACTIVE = 101

        const val MAX_POOL_SIZE = 20
    }
}