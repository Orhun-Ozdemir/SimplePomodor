package com.example.simplepomodor.ui.fragments.pomodoroList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.simplepomodor.R
import com.example.simplepomodor.ui.fragments.pomodore.PomodoreFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PomodoroListFragment : Fragment() {


    private lateinit var fab:FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view:View =inflater.inflate(R.layout.fragment_pomodor_list, container, false)

        fab=view.findViewById(R.id.fab_add_pomodoro)

        fab.setOnClickListener{

            replaceFragment()
        }
        return view
    }

    private fun replaceFragment(){

        var fragmentManager:FragmentManager=parentFragmentManager
        var fragmentTransaction:FragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frame,PomodoreFragment.newInstance(),"Create").commit()
        fragmentTransaction.addToBackStack("Create")




    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PomodoroListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}