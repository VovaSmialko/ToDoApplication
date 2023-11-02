package com.example.todoapplication.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ActivityMainBinding
import com.example.todoapplication.domain.ToDoItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ToDoItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.toDoList.observe(this) {
            toDoListAdapter.submitList(it)
        }
        binding.buttonAddTodoItem.setOnClickListener {
            if (isOnePainMode()) {
                val intent = ToDoItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ToDoItemFragment.newInstanceAddItem())
            }
        }
        val tvCompletedItem = findViewById<TextView>(R.id.show_completed)
        tvCompletedItem.setOnClickListener {
            val intent = ToDoItemCompleted.newIntentShowCompleted(this)
            startActivity(intent)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePainMode(): Boolean {
        return binding.todoItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.todo_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun setupRecyclerView() {
        with(binding.rvTodoList) {
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
        setupSwipeListener(binding.rvTodoList)
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
            if (isOnePainMode()) {
                val intent = ToDoItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ToDoItemFragment.newInstanceEdiItem(it.id))
            }
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