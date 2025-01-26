package com.example.simpletasks.view

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
import com.example.simpletasks.viewmodel.TasksViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, tasksViewModel: TasksViewModel) {
    var subject by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center // This centers the content horizontally
                    ) {
                        Text(
                            text = "Dodaj Nowy",
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
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Nazwa Przedmiotu") }
            )
            OutlinedTextField(
                value = grade,
                onValueChange = { grade = it },
                label = { Text("Ocena") }
            )
            Button(onClick = {
                tasksViewModel.insert(Task(subject = subject, grade = grade))
                navController.popBackStack()
            }) {
                Text(
                    text = "Zapisz",
                    fontSize = 20.sp
                )
            }
        }
    }
}
