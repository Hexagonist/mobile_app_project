package com.example.simpletasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletasks.view.AddTaskScreen
import com.example.simpletasks.view.EditTaskScreen
import com.example.simpletasks.view.MainScreen
import com.example.simpletasks.model.TaskDatabase
import com.example.simpletasks.model.TaskRepository
import com.example.simpletasks.viewmodel.TasksViewModel
import com.example.simpletasks.viewmodel.GradeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy { TaskDatabase.getDatabase(this) }
        val repository by lazy { TaskRepository(database.gradeDao()) }

        setContent {
            val navController = rememberNavController()
            val tasksViewModel: TasksViewModel = viewModel(
                factory = GradeViewModelFactory(repository)
            )

            NavHost(navController = navController, startDestination = "grades") {
                composable("grades") {
                    MainScreen(navController, tasksViewModel)
                }
                composable("add") {
                    AddTaskScreen(navController, tasksViewModel)
                }
                composable("edit/{gradeId}") { backStackEntry ->
                    val gradeId = backStackEntry.arguments?.getString("gradeId")?.toIntOrNull() ?: 0
                    EditTaskScreen(navController, tasksViewModel, gradeId)
                }
            }
        }
    }
}
