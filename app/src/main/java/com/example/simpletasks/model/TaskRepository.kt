package com.example.simpletasks.model

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao, private val categoryDao: CategoryDao) {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
}
