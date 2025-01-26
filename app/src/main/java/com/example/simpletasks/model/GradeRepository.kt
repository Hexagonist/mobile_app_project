package com.example.simpletasks.model

import androidx.lifecycle.LiveData
import com.example.simpletasks.model.Grade
import com.example.simpletasks.model.GradeDao

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
