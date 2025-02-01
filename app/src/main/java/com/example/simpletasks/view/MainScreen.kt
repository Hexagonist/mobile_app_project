package com.example.simpletasks.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simpletasks.model.Task
import com.example.simpletasks.viewmodel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.allTasks.observeAsState(listOf())

//    // Calculate the mean dynamically
//    val meanGrade = grades
//        .mapNotNull { it.grade.toDoubleOrNull() }
//        .average()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center // This centers the content horizontally
                    ) {
                        Text(
                            text = "Zadania",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // LazyColumn to display grades
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasks) { task ->
                    TaskRow(task, onEdit = {
                        navController.navigate("edit/${task.id}")
                    })
                }
            }

            // Row at the bottom to display the mean
//            MeanRow(meanGrade)
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                onClick = { navController.navigate("add") })
            {
                Text(
                    text = "NOWY",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit() },
//        elevation = 4.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = task.title,
                    fontSize = 20.sp
                )
            }
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = task.categoryName,
                    fontSize = 20.sp
                )
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


