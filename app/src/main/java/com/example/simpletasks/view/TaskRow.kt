package com.example.simpletasks.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletasks.R
import com.example.simpletasks.model.Task

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
                            0 -> R.drawable.double_arrow_down // Very Low importance
                            1 -> R.drawable.arrow_down // Low importance
                            2 -> R.drawable.outline_circle_24 // Medium importance
                            3 -> R.drawable.arrow_up // High importance
                            4 -> R.drawable.double_arrow_up_red // Very High importance
                            else -> R.drawable.outline_circle_24 // Default
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pozostało: " + task.color.toString() + " dni",
                fontSize = 20.sp,
                fontWeight = if (task.isDone) FontWeight.Normal else FontWeight.Bold,
                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                modifier =  Modifier.padding(16.dp)
            )
        }
    }
}