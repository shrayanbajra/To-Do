package com.example.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvTasks: RecyclerView = findViewById(R.id.rv_tasks)
        val adapter = MainAdapter()

        rvTasks.adapter = adapter
        rvTasks.layoutManager = LinearLayoutManager(this)

        val values = listOf("Hello", "There", "How", "Are", "You", "?")

        adapter.update(values)
    }
}
