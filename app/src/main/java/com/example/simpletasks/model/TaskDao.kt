package com.example.simpletasks.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM grades")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM grades WHERE categoryId = :categoryId")
    fun getTasksByCategory(categoryId: Int): LiveData<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}
