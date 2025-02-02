package com.example.simpletasks.view

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletasks.R
import com.example.simpletasks.viewmodel.TaskViewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit
import android.content.Context


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(navController: NavController, tasksViewModel: TaskViewModel, taskId: Int, context: Context) {
    var task = tasksViewModel.allTasks.observeAsState(listOf()).value.firstOrNull { it.id.toInt() == taskId }

    var title by remember { mutableStateOf(task?.title ?: "") }
    var color by remember { mutableStateOf(task?.color ?: 0) }
    var category by remember { mutableStateOf(task?.categoryName ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var importance by remember { mutableStateOf(task?.importance ?: 0) }
    var selectedDate by remember { mutableStateOf("") }
    var daysUntilTask by remember { mutableStateOf(0) }

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, { _, pickedYear, pickedMonth, pickedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(pickedYear, pickedMonth, pickedDay)

            val today = Calendar.getInstance()
            val difference = TimeUnit.DAYS.convert(
                selectedCalendar.timeInMillis - today.timeInMillis,
                TimeUnit.MILLISECONDS
            ).toInt()

            daysUntilTask = difference
            selectedDate = "$pickedDay/${pickedMonth + 1}/$pickedYear"
        }, year, month, day).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center // This centers the content horizontally
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
            // Date Picker Button
            Button(onClick = { showDatePicker() }) {
                Text(text = if (selectedDate.isEmpty()) "Termin" else "Termin: $selectedDate")
            }
            // Importance Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { importance = 0 },
                    modifier = Modifier
                        .background(if (importance == 0) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.double_arrow_down),
                        contentDescription = "Very Low Importance"
                    )
                }
                IconButton(
                    onClick = { importance = 1 },
                    modifier = Modifier
                        .background(if (importance == 1) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_down),
                        contentDescription = "Low Importance"
                    )
                }
                IconButton(
                    onClick = { importance = 2 },
                    modifier = Modifier
                        .background(if (importance == 2) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_circle_24),
                        contentDescription = "Medium Importance"
                    )
                }
                IconButton(
                    onClick = { importance = 3 },
                    modifier = Modifier
                        .background(if (importance == 3) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_up),
                        contentDescription = "High Importance"
                    )
                }
                IconButton(
                    onClick = { importance = 4 },
                    modifier = Modifier
                        .background(if (importance == 4) Color.LightGray else Color.Transparent)
                        .border(1.dp, Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.double_arrow_up_red),
                        contentDescription = "Very High Importance"
                    )
                }
            }
            Row {
                Button(onClick = {
                    task?.let {
                        tasksViewModel.update(it.copy(
                            title = title,
                            color = color,
                            categoryName = category,
                            description = description,
                            importance = importance,
//                            isDone = isDone
                        ))
                    }
                    navController.popBackStack()
                },
                    enabled = title.isNotBlank() && category.isNotBlank()
                ) {
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
