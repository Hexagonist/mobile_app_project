package com.example.simpletasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // Auto-generated primary key
    val title: String,
    val color: Int, // Assuming color is stored as an Int (e.g., Color.RED)
    val categoryName: String,
    val description: String,
    val importance: Int,
    val isDone: Boolean
)