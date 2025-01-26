package com.example.simpletasks.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM grades")
    fun getAllGrades(): LiveData<List<Task>>

    @Insert
    suspend fun insertGrade(task: Task)

    @Update
    suspend fun updateGrade(task: Task)

    @Delete
    suspend fun deleteGrade(task: Task)
}
