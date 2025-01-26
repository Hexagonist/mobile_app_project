package com.example.simpletasks.model

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {
    val allGrades: LiveData<List<Task>> = taskDao.getAllGrades()

    suspend fun insert(task: Task) {
        taskDao.insertGrade(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateGrade(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteGrade(task)
    }
}
