package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.repository.TaskRepository

class TaskViewModelFactory(val app : Application, val taskRepository: TaskRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(app, taskRepository) as T
    }
}