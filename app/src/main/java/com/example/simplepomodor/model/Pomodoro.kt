package com.example.simplepomodor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date


@Entity(tableName = "pomodoro")
data class Pomodoro(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var task:String,
    var totalCount: Int,
    var finishedCount: Int,
    var isFinished: Boolean,
    var date: String?,
    var color:String?,
    var pomodoroCircle: ArrayList<PomodoroCircle>
) :Serializable{

}