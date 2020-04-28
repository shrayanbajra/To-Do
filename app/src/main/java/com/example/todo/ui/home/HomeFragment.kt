package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private lateinit var rvTasks: RecyclerView
    private val homeAdapter = HomeAdapter(object : HomeAdapter.OnItemClickListener {
        override fun onItemClick(itemID: Int) {
            // Navigate to View Item Fragment
            Toast.makeText(context, "Item ID -> $itemID", Toast.LENGTH_SHORT).show()
        }
    })

    private lateinit var btnAddTask: FloatingActionButton

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)

        btnAddTask = view.findViewById(R.id.btn_add_task)
    }

    private fun initRecyclerView(view: View) {
        rvTasks = view.findViewById(R.id.rv_tasks)
        rvTasks.adapter = homeAdapter
        rvTasks.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fetchTasks()
        observeTasks()

        navigateToAddTaskFragment()
    }

    private fun fetchTasks() {
        viewModel.fetchTasks()
    }

    private fun observeTasks() {
        viewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            homeAdapter.update(tasks)
        })
    }

    private fun navigateToAddTaskFragment() {
        btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }
    }
}
