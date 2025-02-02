package com.example.simpletasks.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simpletasks.R
import com.example.simpletasks.model.Task
import com.example.simpletasks.viewmodel.TaskViewModel
import kotlinx.coroutines.selects.select
import org.tensorflow.lite.support.label.Category



//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreen(navController: NavController, taskViewModel: TaskViewModel) {
//    val tasks by taskViewModel.allTasks.observeAsState(listOf())
//    var showSortMenu by remember { mutableStateOf(false) } // State for showing the sort menu
//    var sortOption by remember { mutableStateOf("None") } // State for the selected sort option
//
//    // Sort tasks based on the selected option
//    val sortedTasks = when (sortOption) {
//        "Importance" -> tasks.sortedBy { it.importance }
//        "Category" -> tasks.sortedBy { it.categoryName }
//        "Completed" -> tasks.sortedBy { it.isDone }
//        else -> tasks.sortedBy { it.isDone } // Default: no sorting
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "SimpleTasks",
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                },
//                actions = {
//                    // Sort icon button to show the context menu
//                    IconButton(onClick = { showSortMenu = true }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_sort), // Replace with your sort icon
//                            contentDescription = "Sort Tasks"
//                        )
//                    }
//
//                    // Sort context menu
//                    DropdownMenu(
//                        expanded = showSortMenu,
//                        onDismissRequest = { showSortMenu = false }
//                    ) {
//                        DropdownMenuItem(
//                            text = { Text("Sort by Importance") },
//                            onClick = {
//                                sortOption = "Importance"
//                                showSortMenu = false
//                            }
//                        )
//                        DropdownMenuItem(
//                            text = { Text("Sort by Category") },
//                            onClick = {
//                                sortOption = "Category"
//                                showSortMenu = false
//                            }
//                        )
//                        DropdownMenuItem(
//                            text = { Text("Sort by Completed") },
//                            onClick = {
//                                sortOption = "Completed"
//                                showSortMenu = false
//                            }
//                        )
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            // Floating Action Button (FAB)
//            FloatingActionButton(
//                onClick = { navController.navigate("add") },
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = "Add Task"
//                )
//            }
//        }
//    ) { padding ->
//        Column(modifier = Modifier.padding(padding)) {
//            // LazyColumn to display sorted tasks
//            LazyColumn(modifier = Modifier.weight(1f)) {
//                items(sortedTasks) { task ->
//                    TaskRow(
//                        task = task,
//                        onEdit = {
//                            navController.navigate("edit/${task.id}")
//                        },
//                        onToggleDone = { isDone ->
//                            taskViewModel.update(task.copy(isDone = isDone))
//                        }
//                    )
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.allTasks.observeAsState(listOf())
    var showSortMenu by remember { mutableStateOf(false) }
    var sortOption by remember { mutableStateOf("None") }

    // Sort tasks based on the selected option
    val sortedTasks = when (sortOption) {
        "Importance" -> tasks.sortedBy { it.importance }
        "Category" -> tasks.sortedBy { it.categoryName }
        "Completed" -> tasks.sortedBy { it.isDone }
        else -> tasks
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
                            text = "SimpleTasks",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    // Sort icon button to show the context menu
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort),
                            contentDescription = "Sort Tasks"
                        )
                    }

                    // Sort context menu
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sort by Importance") },
                            onClick = {
                                sortOption = "Importance"
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sort by Category") },
                            onClick = {
                                sortOption = "Category"
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sort by Completed") },
                            onClick = {
                                sortOption = "Completed"
                                showSortMenu = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
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
                    modifier = Modifier.background(Color.LightGray)
                )
                // Categories option
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Categories") },
                    label = { Text("Categories") },
                    selected = false,
                    onClick = { navController.navigate("categories") },
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // LazyColumn to display sorted tasks
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(sortedTasks) { task ->
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

@Composable
fun MeanRow(mean: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Średnia Ocen ",
                    style = MaterialTheme.typography.titleLarge

                )
            }
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = if (mean.isNaN()) "N/A" else "%.2f".format(mean),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}


