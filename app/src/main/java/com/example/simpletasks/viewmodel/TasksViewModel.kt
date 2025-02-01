package com.example.simpletasks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.simpletasks.model.Task
import com.example.simpletasks.model.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
//
//    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks().asLiveData()
//
//    fun insert(task: Task) = viewModelScope.launch {
//        taskDao.insert(task)
//    }
//
//    fun update(task: Task) = viewModelScope.launch {
//        taskDao.update(task)
//    }
//
//    fun delete(task: Task) = viewModelScope.launch {
//        taskDao.deleteTaskById(task.id)
//    }
//}


class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

//    val allTasks: Flow<List<Task>> = repository.allTasks
    val allTasks: LiveData<List<Task>> = repository.allTasks

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun deleteAllTasks() = viewModelScope.launch {
        repository.deleteAllTasks()
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
