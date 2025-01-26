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
import com.example.simpletasks.model.Grade
import com.example.simpletasks.viewmodel.TasksViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, tasksViewModel: TasksViewModel) {
    val grades by tasksViewModel.allGrades.observeAsState(listOf())

    // Calculate the mean dynamically
    val meanGrade = grades
        .mapNotNull { it.grade.toDoubleOrNull() }
        .average()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center // This centers the content horizontally
                    ) {
                        Text(
                            text = "Moje Oceny",
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
                items(grades) { grade ->
                    GradeRow(grade, onEdit = {
                        navController.navigate("edit/${grade.id}")
                    })
                }
            }

            // Row at the bottom to display the mean
            MeanRow(meanGrade)
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
fun GradeRow(grade: Grade, onEdit: () -> Unit) {
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
                    text = grade.subject,
                    fontSize = 20.sp
                )
            }
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = grade.grade,
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


