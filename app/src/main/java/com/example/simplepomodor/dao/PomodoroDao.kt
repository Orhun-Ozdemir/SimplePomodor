package com.example.simplepomodor.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplepomodor.model.Pomodoro

@Dao
interface PomodoroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPomodoro(pomodoro: Pomodoro)
    @Query("Select * from pomodoro")
    fun getPomodoroList():List<Pomodoro>

    @Delete()
    fun deletePomodooro(pomodoro: Pomodoro)
}