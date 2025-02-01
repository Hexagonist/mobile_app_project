package com.example.simpletasks.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreen(navController: NavController, taskViewModel: TaskViewModel) {
//    val tasks by taskViewModel.allTasks.observeAsState(listOf())
//
////    // Calculate the mean dynamically
////    val meanGrade = grades
////        .mapNotNull { it.grade.toDoubleOrNull() }
////        .average()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center // This centers the content horizontally
//                    ) {
//                        Text(
//                            text = "SimpleTasks",
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            // Floating Action Button (FAB)
//            FloatingActionButton(
//                onClick = { navController.navigate("add") }, // Navigate to the "add" screen
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add, // Use the default "add" icon
//                    contentDescription = "Add Task" // Accessibility description
//                )
//            }
//        }) { padding ->
//        Column(modifier = Modifier.padding(padding)) {
//            // LazyColumn to display grades
//            LazyColumn(modifier = Modifier.weight(1f)) {
//                items(tasks) { task ->
//                    TaskRow(task,
//                        onEdit = {
//                        navController.navigate("edit/${task.id}")
//                    },
//                    onToggleDone = { isDone ->
//                        // Update the task in the database
//                        taskViewModel.update(task.copy(isDone = isDone))
//                    })
//                }
//            }
//
////            // Row at the bottom to display the mean
//////            MeanRow(meanGrade)
////            Button(modifier = Modifier
////                .fillMaxWidth()
////                .padding(8.dp),
////                onClick = { navController.navigate("add") })
////            {
////                Text(
////                    text = "NOWE",
////                    fontSize = 20.sp
////                )
////            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.allTasks.observeAsState(listOf())
    var showSortMenu by remember { mutableStateOf(false) } // State for showing the sort menu
    var sortOption by remember { mutableStateOf("None") } // State for the selected sort option

    // Sort tasks based on the selected option
    val sortedTasks = when (sortOption) {
        "Importance" -> tasks.sortedBy { it.importance }
        "Category" -> tasks.sortedBy { it.categoryName }
        "Completed" -> tasks.sortedBy { it.isDone }
        else -> tasks.sortedBy { it.isDone } // Default: no sorting
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
                            painter = painterResource(id = R.drawable.ic_sort), // Replace with your sort icon
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
            // Floating Action Button (FAB)
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
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
fun TaskRow(task: Task, onEdit: () -> Unit, onToggleDone: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit() },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Importance Image
            Column(Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(
                        id = when (task.importance) {
                            0 -> R.drawable.arrow_up // Low importance
                            1 -> R.drawable.double_arrow_up_orange // Medium importance
                            2 -> R.drawable.double_arrow_up_red // High importance
                            else -> R.drawable.arrow_up // Default
                        }
                    ),
                    contentDescription = "Importance"
                )
            }

            // Task Title
            Column(
                Modifier
                    .weight(1f)
                    .padding(16.dp)) {
                Text(
                    text = task.title,
                    fontSize = 20.sp,
                    fontWeight = if (task.isDone) FontWeight.Normal else FontWeight.Bold,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
            }

            // Task Category
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = task.categoryName,
                    fontSize = 20.sp,
                    fontWeight = if (task.isDone) FontWeight.Normal else FontWeight.Bold,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
            }

            // Checkbox for isDone
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { isChecked ->
                    onToggleDone(isChecked) // Call the callback to update the task
                },
                modifier = Modifier.padding(16.dp)
            )
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


