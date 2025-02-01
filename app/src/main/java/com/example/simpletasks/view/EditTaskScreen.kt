package com.example.simpletasks.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletasks.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(navController: NavController, tasksViewModel: TaskViewModel, taskId: Int) {
    var task = tasksViewModel.allTasks.observeAsState(listOf()).value.firstOrNull { it.id.toInt() == taskId }

    var title by remember { mutableStateOf(task?.title ?: "") }
    var category by remember { mutableStateOf(task?.categoryName ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center // This centers the content horizontally
                    ) {
                        Text(
                            text = "Edytuj",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(
                        text = "Nazwa zadania",
                        fontSize = 16.sp
                    )
                }
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = {
                    Text(
                        text = "Ocena",
                        fontSize = 16.sp
                    )
                }
            )
            Row {
                Button(onClick = {
                    task?.let {
                        tasksViewModel.update(it.copy(title = title, categoryName = category))
                    }
                    navController.popBackStack()
                }) {
                    Text(
                        text = "Zapisz",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    task?.let { tasksViewModel.delete(it) }
                    navController.popBackStack()
                }) {
                    Text(
                        text = "Usuń",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
