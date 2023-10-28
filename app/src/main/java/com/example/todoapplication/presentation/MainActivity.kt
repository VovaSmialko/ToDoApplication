package com.example.todoapplication.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.domain.ToDoItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var toDoListAdapter: ToDoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.toDoList.observe(this) {
            toDoListAdapter.submitList(it)
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_todo_item)
        buttonAddItem.setOnClickListener {
            val intent = ToDoItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

        val tvCompletedItem = findViewById<TextView>(R.id.show_completed)
        tvCompletedItem.setOnClickListener {
            val intent = ToDoItemCompleted.newIntentShowCompleted(this)
            startActivity(intent)
        }
    }


    private fun setupRecyclerView() {
        val rvToDoList = findViewById<RecyclerView>(R.id.rv_todo_list)
        with(rvToDoList) {
            toDoListAdapter = ToDoListAdapter()
            adapter = toDoListAdapter
            recycledViewPool.setMaxRecycledViews(
                ToDoListAdapter.VIEW_TYPE_ACTIVE,
                ToDoListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ToDoListAdapter.VIEW_TYPE_COMPLETED,
                ToDoListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvToDoList)
    }

    private fun setupSwipeListener(rvToDoList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = toDoListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteToDoItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvToDoList)
    }

    private fun setupClickListener() {
        toDoListAdapter.onToDOItemClickListener = {
            val intent = ToDoItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        toDoListAdapter.onToDOItemLongClickListener = {
            viewModel.changeCompleteState(it)
        }
    }

    companion object {

        private const val MAIN_SCREEN = "main_screen"
        private const val MODE_MAIN = "mode_main"


        fun newIntentShowActive(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(MAIN_SCREEN, MODE_MAIN)
            return intent
        }
    }
}