package com.example.simpletasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class GradeViewModel(private val repository: GradeRepository) : ViewModel() {
    val allGrades: LiveData<List<Grade>> = repository.allGrades

    fun insert(grade: Grade) = viewModelScope.launch {
        repository.insert(grade)
    }

    fun update(grade: Grade) = viewModelScope.launch {
        repository.update(grade)
    }

    fun delete(grade: Grade) = viewModelScope.launch {
        repository.delete(grade)
    }
}

class GradeViewModelFactory(private val repository: GradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GradeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
