package com.example.simplepomodor.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.simplepomodor.R
import com.example.simplepomodor.model.Pomodoro
import com.example.simplepomodor.ui.fragments.pomodore.PomodoreFragment
import com.google.android.material.card.MaterialCardView


class PomodoroListAdapter(private var itemList: List<Pomodoro>, private var fm: FragmentManager) :
    RecyclerView.Adapter<PomodoroListAdapter.PomodoroViewHolder>() {


    class PomodoroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pomodoroCount: TextView = itemView.findViewById(R.id.tv_item_pomodoro_count)
        var pomodoroTask: TextView = itemView.findViewById(R.id.tv_item_pomodoro_title)
        var pomodoroCard: MaterialCardView = itemView.findViewById(R.id.cv_pomodoro)
        var pomodoroDelete:ImageView=itemView.findViewById(R.id.iv_item_pomodoro_delete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PomodoroViewHolder {
        return PomodoroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pomodoro, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PomodoroViewHolder, position: Int) {
        var pomodoro: Pomodoro = itemList[position]
        holder.pomodoroCount.text = "${pomodoro.finishedCount} / ${pomodoro.totalCount}"
        holder.pomodoroTask.text = "${pomodoro.task.uppercase()}"
        holder.pomodoroCard.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("pomodoro", pomodoro)
            var pomodoroFragment = PomodoreFragment.newInstance()
            pomodoroFragment.arguments = bundle
            fm.beginTransaction().replace(R.id.main_frame, pomodoroFragment).addToBackStack("")
                .commit()

        }

        holder.pomodoroDelete.setOnClickListener{

            //TODO:Room Database delete işlemi yapılıcak
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

