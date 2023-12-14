package com.example.simplepomodor.ui.fragments.pomodore

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplepomodor.R
import com.example.simplepomodor.adapter.PomodoroCircleCountAdapter
import com.example.simplepomodor.databinding.FragmentPomodoreBinding
import com.example.simplepomodor.model.Pomodoro

class PomodoreFragment : Fragment() {


    private lateinit var pomodoro: Pomodoro
    private lateinit var viewModel: PomodoroViewModel
    private lateinit var pomodoroBinding: FragmentPomodoreBinding
    private lateinit var pomodoroCircleCountAdapter: PomodoroCircleCountAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {

        // TODO: ViewModel araştıulıcak dökümanada neler var bakılıcak
        //
        if (arguments?.containsKey("pomodoro") == true) {
            pomodoro = requireArguments().getSerializable("pomodoro") as Pomodoro
        }
        viewModel = PomodoroViewModel(pomodoro,requireActivity().application)
        //ViewModelProvider(this)[PomodoroViewModel::class.java]
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.clock_sound)
        pomodoroCircleCountAdapter =
            PomodoroCircleCountAdapter(requireContext(), pomodoro.pomodoroCircle)

        val view: View = inflater.inflate(R.layout.fragment_pomodore, container, false)
        pomodoroBinding = FragmentPomodoreBinding.bind(view)


        pomodoroBinding.btnStopTimer.text = getString(R.string.reset)
        pomodoroBinding.rvPomodoroCountCircleList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        pomodoroBinding.rvPomodoroCountCircleList.adapter = pomodoroCircleCountAdapter


        bindView()
        return view
    }

    private fun bindView() {

        pomodoroBinding.btnStartTimer.setOnClickListener { viewModel.startTimer() }
        pomodoroBinding.btnStopTimer.setOnClickListener { viewModel.resetTimer() }
        pomodoroBinding.ivVolume.setOnClickListener { viewModel.changeMediaPlayerStatus() }
        pomodoroBinding.ivPomodoroSkipNext.setOnClickListener { viewModel.skipNext() }

        setListenersToView()
    }

    private fun setListenersToView(){

        viewModel.isSoundOn.observe(viewLifecycleOwner) {
            if (it) {
                pomodoroBinding.ivVolume.setImageResource(R.drawable.volume_up)
                playMedia()
            } else {
                pomodoroBinding.ivVolume.setImageResource(R.drawable.volume_off)
                stopMediaPlayer()
            }
        }
        viewModel.startTimerText.observe(viewLifecycleOwner) {
            pomodoroBinding.btnStartTimer.text = getString(it)
        }
        viewModel.fullWatch.observe(viewLifecycleOwner) {
            pomodoroBinding.tvTimeView.text = it
        }
        viewModel.totalTime.observe(viewLifecycleOwner) {
            pomodoroBinding.pmCpi.max = it.toInt()
        }
        viewModel.totalProgress.observe(viewLifecycleOwner) {
            pomodoroBinding.pmCpi.progress = it.toInt()
            playMedia()
        }
        viewModel.primaryColor.observe(viewLifecycleOwner){
            pomodoroBinding.root.setBackgroundResource(it)
        }
        viewModel.pomodoroCurrentStatus.observe(viewLifecycleOwner){
            pomodoroBinding.tvPomodoroStatus.text=getString(it)
        }
        viewModel.secondaryColor.observe(viewLifecycleOwner){
            pomodoroBinding.pmCpi.trackColor=ContextCompat.getColor(requireContext(),it)
        }
        viewModel.pomodoroCircleList.observe(viewLifecycleOwner) {
            pomodoroCircleCountAdapter = PomodoroCircleCountAdapter(requireContext(), it)
            pomodoroBinding.rvPomodoroCountCircleList.adapter = pomodoroCircleCountAdapter
            pomodoroCircleCountAdapter.notifyDataSetChanged()
        }
    }

    private fun playMedia() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    private fun stopMediaPlayer() {
        mediaPlayer.stop()
    }

    override fun onStop() {
        mediaPlayer.release()
        super.onStop()
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