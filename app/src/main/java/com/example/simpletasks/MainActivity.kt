package com.example.simpletasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletasks.view.AddGradeScreen
import com.example.simpletasks.view.EditGradeScreen
import com.example.simpletasks.view.MainScreen
import com.example.simpletasks.model.GradeDatabase
import com.example.simpletasks.model.GradeRepository
import com.example.simpletasks.viewmodel.TasksViewModel
import com.example.simpletasks.viewmodel.GradeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy { GradeDatabase.getDatabase(this) }
        val repository by lazy { GradeRepository(database.gradeDao()) }

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
                    AddGradeScreen(navController, tasksViewModel)
                }
                composable("edit/{gradeId}") { backStackEntry ->
                    val gradeId = backStackEntry.arguments?.getString("gradeId")?.toIntOrNull() ?: 0
                    EditGradeScreen(navController, tasksViewModel, gradeId)
                }
            }
        }
    }
}
