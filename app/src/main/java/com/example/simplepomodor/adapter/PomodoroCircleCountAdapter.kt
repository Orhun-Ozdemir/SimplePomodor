package com.example.simplepomodor.adapter

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplepomodor.R
import com.example.simplepomodor.model.PomodoroCircle
import com.example.simplepomodor.model.modelEnum.PomodoroCircleStatus
import com.google.android.material.card.MaterialCardView

class PomodoroCircleCountAdapter(
    private var context: Context,
    private var pomodoroCircle: ArrayList<PomodoroCircle>
) :
    RecyclerView.Adapter<PomodoroCircleCountAdapter.PomodoroCircleViewHolder>() {


    class PomodoroCircleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var vCircle: View = view.findViewById(R.id.cv_pomodoro_count_circle)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PomodoroCircleViewHolder {
        return PomodoroCircleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_count_circle, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pomodoroCircle.size
    }

    override fun onBindViewHolder(holder: PomodoroCircleViewHolder, position: Int) {
        var pomodoroCircle = pomodoroCircle[position]
        when (pomodoroCircle.status) {

            PomodoroCircleStatus.EMPTY -> {
                holder.vCircle.setBackgroundResource(R.drawable.empty_pomodoro)
            }

            PomodoroCircleStatus.QUARTER -> {
                holder.vCircle.setBackgroundResource(R.drawable.pomodo_quarter)

            }
            PomodoroCircleStatus.HALF -> {
                holder.vCircle.setBackgroundResource(R.drawable.pomodoro_half)

            }
            PomodoroCircleStatus.THREEQUARTERS -> {
                holder.vCircle.setBackgroundResource(R.drawable.three_quarter_pomodoro)

            }
            PomodoroCircleStatus.WHOLE -> {
                holder.vCircle.setBackgroundResource(R.drawable.full_pomodoro)

            }

        }

    }
}