package com.example.simpletasks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Grade::class], version = 1)
abstract class GradeDatabase : RoomDatabase() {
    abstract fun gradeDao(): GradeDao

    companion object {
        @Volatile
        private var INSTANCE: GradeDatabase? = null

        fun getDatabase(context: Context): GradeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GradeDatabase::class.java,
                    "grade_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
