package com.example.tasksapp

import CustomAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemsViewModel>()


        val addButton = findViewById<Button>(R.id.addButton)


        addButton.setOnClickListener {
            val task = findViewById<EditText>(R.id.taskInput)
            data.add(ItemsViewModel(task.text.toString()))
            val adapter = CustomAdapter(data)
            recyclerview.adapter = adapter
            task.setText("")
        }
    }
}