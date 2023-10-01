package com.example.simplepomodor

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.progressindicator.CircularProgressIndicator

class PomodoreFragment : Fragment() {

    private lateinit var cpi:CircularProgressIndicator
    private lateinit var btnStartTimer:Button
    private lateinit var btnStopTimer:Button
    private lateinit var etTimeInput:EditText
    private lateinit var tvTime:TextView
    private var totalTime=0
    private var incrementTime: Long = 1000
    lateinit var countDownTimer: CountDownTimer
    private var timerRunning:Boolean=false
    private var timerPause:Boolean=false
    private var resetTime:Boolean=false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_pomodore,container,false)
        cpi=view.findViewById(R.id.pm_cpi)
        btnStartTimer=view.findViewById(R.id.btn_start_timer)
        btnStopTimer=view.findViewById(R.id.btn_stop_timer)
        etTimeInput=view.findViewById(R.id.et_set_timer)
        tvTime=view.findViewById(R.id.tv_time_view)
        btnStopTimer.text=getString(R.string.reset)
        cpi.setProgress(100,false)



        // Inflate the layout for this fragment
        btnStartTimer.setOnClickListener{ startTimer() }
        btnStopTimer.setOnClickListener { resetTimer() }

        return view
    }




    private fun startTimer(){

       if(etTimeInput.text.isNotEmpty()) {

           if(!timerRunning) {
               if (!timerRunning && timerPause) {

                   prepareCountDown((totalTime - cpi.progress).toLong(), incrementTime)
                   btnStartTimer.text = getString(R.string.pause)
                   btnStartTimer.setBackgroundColor(Color.GRAY)

               } else {
                   timerPause = false
                   timerRunning = true
                   val time: Int = etTimeInput.text.toString().toInt()
                   totalTime = time * 60 * 1000
                   cpi.max = totalTime
                   prepareCountDown(totalTime.toLong(), incrementTime)
                   btnStartTimer.text = getString(R.string.pause)
                   btnStartTimer.setBackgroundColor(Color.GRAY)

               }
           }else{
               stopTimer()
           }
       }
    }

    private fun stopTimer(){
        timerPause=true
        timerRunning=false
        countDownTimer.cancel()
        btnStartTimer.text=getText(R.string.resume)
        btnStartTimer.setBackgroundColor(Color.parseColor("#428438"))
    }

    private fun resetTimer(){

        cpi.setProgress(0,true)
        countDownTimer.cancel()
        tvTime.text="00:00"
        btnStartTimer.text=getString(R.string.start)
        btnStartTimer.setBackgroundColor(Color.parseColor("#428438"))
        timerRunning=false

    }

    private fun prepareCountDown(totalTime:Long, incrementTime:Long)
    {

        countDownTimer = object : CountDownTimer(totalTime, incrementTime) {
            override fun onTick(millisUntilFinished: Long) {
                cpi.setProgress((cpi.max - millisUntilFinished).toInt(), true)
                var seconds = ((cpi.max - millisUntilFinished).toInt() / 1000) % 60
                var minutes = ((cpi.max - millisUntilFinished).toInt() / (1000 * 60)) % 60
                var charcterMinutes=""
                var charcterSeconds=""
                if(minutes.toString().length>1){
                     charcterMinutes=minutes.toString();

                }else{
                charcterMinutes= "0$minutes"
                }
                if(seconds.toString().length>1){
                    charcterSeconds=seconds.toString();

                }else{
                    charcterSeconds= "0$seconds"
                }


                tvTime.text=charcterMinutes+":"+charcterSeconds
            }
            override fun onFinish() {
                cpi.setProgress(0, true)
                countDownTimer.start()
            }
        }
        countDownTimer.start()
    }




    companion object {
        @JvmStatic
        fun newInstance() =
            PomodoreFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}