package com.example.tasksapp

import CustomAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences("task_app", MODE_PRIVATE)
        val tasksString = sharedPreferences.getString("tasks", "") ?: ""
        val editor = sharedPreferences.edit()

        val tasks = tasksString.split(",").toMutableList()
        val data = tasks.map { ItemsViewModel(it) }.toMutableList()

        val adapter = CustomAdapter(data)
        recyclerview.adapter = adapter

        val addButton = findViewById<Button>(R.id.addButton)
        val taskInput = findViewById<EditText>(R.id.taskInput)


        addButton.setOnClickListener {
            data.add(ItemsViewModel(taskInput.text.toString()))
            adapter.notifyDataSetChanged()
            taskInput.setText("")

            val tasksString = data.joinToString(",") { it.text }
            editor.putString("tasks", tasksString)
            editor.apply()
        }

        taskInput.addTextChangedListener {
            addButton.isEnabled = taskInput.text.toString() != ""
        }
        taskInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                // The same action as your addButton
                data.add(ItemsViewModel(taskInput.text.toString()))
                adapter.notifyDataSetChanged()
                taskInput.setText("")

                val tasksStringUpdated = data.joinToString(",") { it.text }
                editor.putString("tasks", tasksStringUpdated)
                editor.apply()

                true // consume the event
            } else {
                false // let other events be handled by the framework
            }
        }

    }
}