package com.example.simplepomodor.ui.fragments.pomodore

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplepomodor.R
import com.example.simplepomodor.model.Pomodoro
import com.example.simplepomodor.model.PomodoroCircle
import com.example.simplepomodor.model.modelEnum.PomodoroCircleStatus
import com.example.simplepomodor.repositories.database.PomodoroDatabase

class PomodoroViewModel(var pomodoro: Pomodoro, application: Application) : ViewModel() {

    var pomodoroCircleList: MutableLiveData<ArrayList<PomodoroCircle>> =
        MutableLiveData<ArrayList<PomodoroCircle>>()
    var totalTime: MutableLiveData<Long> = MutableLiveData<Long>()
    var totalProgress: MutableLiveData<Long> = MutableLiveData<Long>()
    var fullWatch: MutableLiveData<String> = MutableLiveData<String>()
    var startTimerText: MutableLiveData<Int> = MutableLiveData<Int>()



    private var roomDatabase: PomodoroDatabase? = PomodoroDatabase.newInstance(application)

   //TODO:Settings Sayfası için Yapılıcak Değerler
    var isSoundOn: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var primaryColor: MutableLiveData<Int> = MutableLiveData<Int>()
    var secondaryColor: MutableLiveData<Int> = MutableLiveData<Int>()
    var pomodoroCurrentStatus: MutableLiveData<Int> = MutableLiveData<Int>()



    init {
        pomodoroCircleList.value = pomodoro.pomodoroCircle
        pomodoroCurrentStatus.value=R.string.pomodoro
        totalTime.value=1500000
        prepareCountDown(totalTime.value!!,1000)
        isSoundOn.value = false

    }


    private var incrementTime: Long = 1000
    private lateinit var countDownTimer: CountDownTimer
    private var timerRunning: Boolean = false
    private var timerPause: Boolean = false
    private var resetTime: Boolean = false
    private var timerStatus: Boolean = false

    var isProcessing: Boolean = true;



    //TODO:LiveData nasıl kullanılır
    //TODO:ViewModel nasıl kullanılır
    //TODO:DataBindidng nasıl kullanılır


    fun startTimer() {
        if (!timerRunning) {
            if (!timerRunning && timerPause) {
                //TODO:RESUME_POMODORO
                timerRunning = true
                timerPause = false
                timerStatus = true
                prepareCountDown(
                    (totalTime.value!! - totalProgress.value!!),
                    incrementTime
                )
                countDownTimer.start()
                startTimerText.value = R.string.pause
                //btnStartTimer.setBackgroundColor(Color.GRAY)

            } else {
                totalProgress.value = 0;
                //TODO:START_POMODORO
                timerPause = false
                timerRunning = true
                prepareCountDown(totalTime.value!!, incrementTime)
                countDownTimer.start()
                startTimerText.value = R.string.pause
                //btnStartTimer.setBackgroundColor(Color.GRAY)

            }
        } else {
            //TODO:STOP_POMODORO
            stopTimer()
        }

    }

    fun skipNext(){
        countDownTimer.onFinish()
        resetTimer()
    }

    fun changeMediaPlayerStatus() {
        isSoundOn.value = !isSoundOn.value!!
    }

    private fun stopTimer() {
        timerPause = true
        timerRunning = false
        countDownTimer.cancel()
        startTimerText.value = R.string.resume
        //btnStartTimer.setBackgroundColor(Color.parseColor("#428438"))
    }
    fun resetTimer() {
        totalProgress.value = 0
        countDownTimer.cancel()
        fullWatch.value = "00:00"
        startTimerText.value = R.string.start
        //btnStartTimer.setBackgroundColor(Color.parseColor("#428438"))
        timerRunning = false
    }

    private fun prepareCountDown(time: Long, incrementTime: Long) {
        countDownTimer = object : CountDownTimer(time, incrementTime) {
            override fun onTick(millisUntilFinished: Long) {

                totalProgress.value =
                    if (!timerStatus) time - millisUntilFinished else (totalProgress.value
                        ?: 0) + (incrementTime)
                var value = totalProgress.value!!
                val passedSecond: String =
                    (((value).toInt() / 1000) % 60).toString()
                val passedMinute: String =
                    (((value).toInt() / (1000 * 60)) % 60).toString()


                val formattedSeconds =
                    if (passedSecond.length > 1) passedSecond else "0$passedSecond"
                val formattedMinutes =
                    if (passedMinute.length > 1) passedMinute else "0$passedMinute"

                fullWatch.value = "$formattedMinutes:$formattedSeconds"

                if (totalProgress.value!! > time / 4 && totalProgress.value!! < time / 2) {
                    checkCircleAndUpdate(PomodoroCircleStatus.QUARTER)
                } else if (totalProgress.value!! > time / 2 && totalProgress.value!! < time * 3 / 4) {
                    checkCircleAndUpdate(PomodoroCircleStatus.HALF)
                } else if (totalProgress.value!! < time && totalProgress.value!! > time * 3 / 4) {
                    checkCircleAndUpdate(PomodoroCircleStatus.THREEQUARTERS)
                }
            }

            override fun onFinish() {
                timerStatus = false
                totalProgress.value = totalTime.value
                if(pomodoroCurrentStatus.value!=null&&pomodoroCurrentStatus.value==R.string.pomodoro) {
                    primaryColor.value = R.color.shortDefaultPrimaryColor
                    secondaryColor.value = R.color.shortDefaultSecondaryColor
                    pomodoroCurrentStatus.value = R.string.short_break
                    totalTime.value=300000
                    checkStatusAndUpdateCircle(PomodoroCircleStatus.WHOLE)
                }else{
                    primaryColor.value = R.color.pomodoroDefaultPrimaryColor
                    secondaryColor.value = R.color.pomodoroDefaultSecondaryColor
                    pomodoroCurrentStatus.value = R.string.pomodoro
                    totalTime.value=1500000
                }



            }
        }


    }

    private fun checkStatusAndUpdateCircle(status: PomodoroCircleStatus) {

        if (isProcessing) {
            checkCircleAndUpdate(status)
            isProcessing = false;
            totalTime.value = 300000
        } else {
            isProcessing = true;
            totalTime.value = 1500000
        }

    }


    private fun checkCircleAndUpdate(status: PomodoroCircleStatus) {

        for (pomodoroCircle in pomodoroCircleList.value!!) {
            if (pomodoroCircle.status == PomodoroCircleStatus.WHOLE) {
            } else {
                pomodoroCircle.status = status
                break
            }
        }
        pomodoroCircleList.value = pomodoroCircleList.value
        if (status == PomodoroCircleStatus.WHOLE) {
            pomodoro.finishedCount = pomodoro.finishedCount + 1;
        }
        updatePomodoroStatus()

    }


    fun updatePomodoroStatus() {
        pomodoro.pomodoroCircle = pomodoroCircleList.value!!
        roomDatabase?.pomodoroDao()?.addPomodoro(pomodoro)
    }


}