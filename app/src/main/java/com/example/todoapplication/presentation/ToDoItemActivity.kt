package com.example.todoapplication.presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.parseIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.todoapplication.R
import com.example.todoapplication.domain.ToDoItem
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

class ToDoItemActivity : AppCompatActivity(), ToDoItemFragment.OnEditingFinishedListener {


    private var screenMode = MODE_UNKNOWN
    private var toDoItemId = ToDoItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ToDoItemFragment.newInstanceEdiItem(toDoItemId)
            MODE_ADD -> ToDoItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.todo_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_TODO_ITEM_ID)) {
                throw RuntimeException("Param todo item id is absent")
            }
            toDoItemId = intent.getIntExtra(EXTRA_TODO_ITEM_ID, ToDoItem.UNDEFINED_ID)
        }
    }


    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_TODO_ITEM_ID = "extra_todo_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ToDoItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, toDoItemId: Int): Intent {
            val intent = Intent(context, ToDoItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_TODO_ITEM_ID, toDoItemId)
            return intent
        }
    }
}