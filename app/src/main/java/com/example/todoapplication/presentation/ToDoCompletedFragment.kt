package com.example.todoapplication.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todoapplication.R

class ToDoCompletedFragment : Fragment() {

    private lateinit var viewModel: ToDoCompletedViewModel
    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var tvShowActive: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_completed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModel = ViewModelProvider(this)[ToDoCompletedViewModel::class.java]
        viewModel.toDoList.observe(viewLifecycleOwner) {
            toDoListAdapter.submitList(it)
        }
        launchMainScreen()
    }

    private fun launchMainScreen() {
        tvShowActive.setOnClickListener {
            val intent =
                context?.let { it1 -> MainActivity.newIntentShowActive(it1.applicationContext) }
            startActivity(intent)
        }
    }

    private fun initViews(view: View) {
        tvShowActive = view.findViewById(R.id.show_active)
    }


    companion object {

        private const val EXTRA_SCREEN_COMPLETED = "extra_completed"
        private const val MODE_COMPLETED = "mode_completed"


        fun newIntentShowCompleted(context: Context): ToDoItemCompleted {
            val intent = Intent(context, ToDoItemCompleted::class.java)
            intent.putExtra(EXTRA_SCREEN_COMPLETED, MODE_COMPLETED)
            return ToDoItemCompleted()
        }
    }
}