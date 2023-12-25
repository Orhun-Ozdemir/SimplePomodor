package com.example.simplepomodor.ui.fragementFactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.simplepomodor.ui.fragments.pomodore.PomodoreFragment
import com.example.simplepomodor.ui.fragments.pomodoroList.PomodoroListFragment

class SimpleFragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PomodoreFragment::class.java.name -> {
                PomodoreFragment()
            }

            PomodoroListFragment::class.java.name -> {
                PomodoroListFragment();
            }

            else -> super.instantiate(classLoader, className)


        }

    }
}