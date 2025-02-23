package com.example.todoapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.model.TaskDb
import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.viewmodel.TaskViewModel
import com.example.todoapp.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    lateinit var taskViewModel: TaskViewModel
    lateinit var taskAdapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskRepository = TaskRepository(TaskDb(this))
        val taskViewModelFactory = TaskViewModelFactory(application, taskRepository)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory)[TaskViewModel::class.java]
        
        setupRecyclerView()
        
        binding.fab.setOnClickListener { 
            onFabCLicked()
        }
        
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskAdapter.differ.currentList[position]

                taskViewModel.deleteTask(task)
                Snackbar.make(binding.root, "Removed task", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        taskViewModel.insertTask(task)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.recyclerView)
        }

        taskViewModel.getAllTasks().observe(this){ tasks ->
            taskAdapter.differ.submitList(tasks)
        }

    }

    private fun onFabCLicked() {
        val firstFragment = FirstFragment()
        firstFragment.show(supportFragmentManager, "FirstFragmentTag")
    }

    private fun setupRecyclerView(){
        taskAdapter = TaskAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(application)
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }
}