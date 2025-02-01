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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.simpletasks.R
import com.example.simpletasks.viewmodel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, tasksViewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(0) }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var importance by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center // This centers the content horizontally
                    ) {
                        Text(
                            text = "Dodaj Nowe Zadanie",
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
                label = { Text("Nazwa Zadania") }
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Kategoria") }
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Treść") }
            )
            // Importance Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Button for Importance 0
                IconButton(
                    onClick = { importance = 0 },
                    modifier = Modifier
                        .background(if (importance == 0) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_up), // Replace with your drawable resource
                        contentDescription = "Low Importance"
                    )
                }
                // Button for Importance 1
                IconButton(
                    onClick = { importance = 1 },
                    modifier = Modifier
                        .background(if (importance == 1) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.double_arrow_up_orange), // Replace with your drawable resource
                        contentDescription = "Medium Importance"
                    )
                }

                // Button for Importance 2
                IconButton(
                    onClick = { importance = 2 },
                    modifier = Modifier
                        .background(if (importance == 2) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.double_arrow_up_red), // Replace with your drawable resource
                        contentDescription = "High Importance"
                    )
                }
            }
            Button(onClick = {
                tasksViewModel.insert(
                    Task(
                        title = title,
                        color = color,
                        categoryName = category,
                        description = description,
                        importance = importance,
                        isDone = false

                    ))
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
