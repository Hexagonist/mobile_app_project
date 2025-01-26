package com.example.simpletasks

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.simpletasks.Grade

@Dao
interface GradeDao {
    @Query("SELECT * FROM grades")
    fun getAllGrades(): LiveData<List<Grade>>

    @Insert
    suspend fun insertGrade(grade: Grade)

    @Update
    suspend fun updateGrade(grade: Grade)

    @Delete
    suspend fun deleteGrade(grade: Grade)
}
