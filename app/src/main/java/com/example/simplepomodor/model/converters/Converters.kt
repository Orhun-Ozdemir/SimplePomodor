package com.example.simplepomodor.model.converters

import androidx.room.TypeConverter
import com.example.simplepomodor.model.PomodoroCircle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    private val type: Type = object : TypeToken<ArrayList<PomodoroCircle>>() {}.type
    private val gson: Gson = Gson()

    @TypeConverter
    fun stringToPomodoroCircle(json: String): ArrayList<PomodoroCircle> {

        return gson.fromJson(json, type)
    }
    @TypeConverter
    fun pomodoroCircleToString(arrayPomodoroCircle: ArrayList<PomodoroCircle>): String {

        return gson.toJson(arrayPomodoroCircle).toString()
    }
}