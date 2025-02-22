package com.example.todoapp.repository

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskDao
import com.example.todoapp.model.TaskDb

class TaskRepository(val db: TaskDb) {

    suspend fun insertTask(task: Task) = db.getTaskDao().insertTask(task)

    suspend fun deleteTask(task: Task) = db.getTaskDao().deleteTask(task)

    fun gatAllTasks() = db.getTaskDao().getAllTasks()

}