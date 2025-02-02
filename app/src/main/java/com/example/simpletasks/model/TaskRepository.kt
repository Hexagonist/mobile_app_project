package com.example.simpletasks.model


import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    // LiveData to observe all tasks
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    // Insert a task
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    // Update a task
    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    // Delete a task
    suspend fun delete(task: Task) {
        taskDao.deleteTaskById(task.id)
    }

    suspend fun deleteDoneTasks() {
        taskDao.deleteDoneTasks()
    }


    // Delete all tasks
    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }


}
