package com.example.simpletasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletasks.ui.AddGradeScreen
import com.example.simpletasks.ui.EditGradeScreen
import com.example.simpletasks.ui.GradesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy { GradeDatabase.getDatabase(this) }
        val repository by lazy { GradeRepository(database.gradeDao()) }

        setContent {
            val navController = rememberNavController()
            val gradeViewModel: GradeViewModel = viewModel(
                factory = GradeViewModelFactory(repository)
            )

            NavHost(navController = navController, startDestination = "grades") {
                composable("grades") {
                    GradesScreen(navController, gradeViewModel)
                }
                composable("add") {
                    AddGradeScreen(navController, gradeViewModel)
                }
                composable("edit/{gradeId}") { backStackEntry ->
                    val gradeId = backStackEntry.arguments?.getString("gradeId")?.toIntOrNull() ?: 0
                    EditGradeScreen(navController, gradeViewModel, gradeId)
                }
            }
        }
    }
}
