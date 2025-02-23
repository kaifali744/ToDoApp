package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Task
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(val app : Application, val taskRepository: TaskRepository): AndroidViewModel(app){

    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }
    fun getAllTasks() = taskRepository.gatAllTasks()
}