package com.example.simpletasks.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.navigation.NavController
import com.example.simpletasks.model.Task
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.simpletasks.R
import com.example.simpletasks.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavController, taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.allTasks.observeAsState(listOf())
    var showCategoryMenu by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All") }

    // Get unique categories from tasks
    val categories = tasks.map { it.categoryName }.distinct()

    // Filter tasks by selected category
    val filteredTasks = if (selectedCategory == "All") {
        tasks
    } else {
        tasks.filter { it.categoryName == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Categories",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    // Category filter button
                    IconButton(onClick = { showCategoryMenu = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort), // Replace with your filter icon
                            contentDescription = "Filter by Category"
                        )
                    }

                    // Category filter menu
                    DropdownMenu(
                        expanded = showCategoryMenu,
                        onDismissRequest = { showCategoryMenu = false }
                    ) {
                        // "All" option
                        DropdownMenuItem(
                            text = { Text("All") },
                            onClick = {
                                selectedCategory = "All"
                                showCategoryMenu = false
                            }
                        )
                        // Other categories
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    showCategoryMenu = false
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                // Home option
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("main") }
                )
                // Categories option
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Categories") },
                    label = { Text("Categories") },
                    selected = true,
                    onClick = { navController.navigate("categories") }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // LazyColumn to display filtered tasks
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredTasks) { task ->
                    TaskRow(
                        task = task,
                        onEdit = {
                            navController.navigate("edit/${task.id}")
                        },
                        onToggleDone = { isDone ->
                            taskViewModel.update(task.copy(isDone = isDone))
                        }
                    )
                }
            }
        }
    }
}