package com.example.simpletasks.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
    var showCategoryDialog by remember { mutableStateOf(false) } // State for showing the dialog
    var selectedCategories by remember { mutableStateOf<Set<String>>(setOf()) } // State for selected categories

    // Get unique categories from tasks
    val categories = tasks.map { it.categoryName }.distinct().sorted()

    // Filter tasks based on selected categories
    val filteredTasks = if (selectedCategories.isEmpty()) {
        tasks
    } else {
        tasks.filter { it.categoryName in selectedCategories }
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
                    IconButton(onClick = { showCategoryDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort), // Replace with your filter icon
                            contentDescription = "Filter by Category"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background // Default background color
            ) {
                // Home option
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { navController.navigate("main") },
                    modifier = Modifier.background(Color.Transparent)
                )
                // Categories option
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Categories") },
                    label = { Text("Categories") },
                    selected = false,
                    onClick = { navController.navigate("categories") },
                    modifier = Modifier.background(Color.LightGray)
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

        // Multi-select category dialog
        if (showCategoryDialog) {
            AlertDialog(
                onDismissRequest = { showCategoryDialog = false },
                title = { Text("Select Categories") },
                text = {
                    Column {
                        categories.forEach { category ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCategories = if (category in selectedCategories) {
                                            selectedCategories - category
                                        } else {
                                            selectedCategories + category
                                        }
                                    }
                                    .padding(8.dp)
                            ) {
                                Checkbox(
                                    checked = category in selectedCategories,
                                    onCheckedChange = { isChecked ->
                                        selectedCategories = if (isChecked) {
                                            selectedCategories + category
                                        } else {
                                            selectedCategories - category
                                        }
                                    }
                                )
                                Text(
                                    text = category,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showCategoryDialog = false }) {
                        Text("Done")
                    }
                }
            )
        }
    }
}