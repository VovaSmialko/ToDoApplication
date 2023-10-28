package com.example.todoapplication.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R

class ToDoItemCompleted : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_item_completed)
        launchMainScreen()
    }

    private fun launchMainScreen() {
        val tvShowActive = findViewById<TextView>(R.id.show_active)
        tvShowActive.setOnClickListener {
            val intent = MainActivity.newIntentShowActive(this)
            startActivity(intent)
        }
    }

    companion object {

        private const val EXTRA_SCREEN_COMPLETED = "extra_completed"
        private const val MODE_COMPLETED = "mode_completed"


        fun newIntentShowCompleted(context: Context): Intent {
            val intent = Intent(context, ToDoItemCompleted::class.java)
            intent.putExtra(EXTRA_SCREEN_COMPLETED, MODE_COMPLETED)
            return intent
        }
    }
}