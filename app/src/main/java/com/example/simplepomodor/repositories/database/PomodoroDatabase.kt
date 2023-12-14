package com.example.simplepomodor.repositories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simplepomodor.dao.PomodoroDao
import com.example.simplepomodor.model.Pomodoro
import com.example.simplepomodor.model.converters.Converters

@Database(entities = [Pomodoro::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PomodoroDatabase : RoomDatabase() {

    abstract fun pomodoroDao():PomodoroDao
    companion object {

        private var pomodoroDatabase: PomodoroDatabase? = null
        fun newInstance(applicationContext: Context): PomodoroDatabase? {
            return if (pomodoroDatabase != null) {
                pomodoroDatabase
            } else {
                pomodoroDatabase =
                    Room.databaseBuilder(applicationContext, PomodoroDatabase::class.java, "simplep.db").allowMainThreadQueries().build()
                pomodoroDatabase
            }

        }
    }

}