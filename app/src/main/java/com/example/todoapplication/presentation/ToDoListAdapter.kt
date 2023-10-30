package com.example.todoapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todoapplication.R
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
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ToDoItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ToDoItemViewHolder, position: Int) {
        val toDoItem = getItem(position)
        viewHolder.view.setOnLongClickListener {
            onToDOItemLongClickListener?.invoke(toDoItem)
            true
        }
        viewHolder.view.setOnClickListener {
            onToDOItemClickListener?.invoke(toDoItem)
        }
        viewHolder.tvName.text = toDoItem.name
        viewHolder.tvCount.text = toDoItem.count.toString()
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