package com.example.simpletasks

import androidx.lifecycle.LiveData
import com.example.simpletasks.Grade
import com.example.simpletasks.GradeDao

class GradeRepository(private val gradeDao: GradeDao) {
    val allGrades: LiveData<List<Grade>> = gradeDao.getAllGrades()

    suspend fun insert(grade: Grade) {
        gradeDao.insertGrade(grade)
    }

    suspend fun update(grade: Grade) {
        gradeDao.updateGrade(grade)
    }

    suspend fun delete(grade: Grade) {
        gradeDao.deleteGrade(grade)
    }
}
