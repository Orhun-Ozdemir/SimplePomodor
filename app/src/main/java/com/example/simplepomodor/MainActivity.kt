package com.example.simplepomodor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.simplepomodor.repositories.database.PomodoroDatabase
import com.example.simplepomodor.ui.fragments.pomodoroList.PomodoroListFragment
import android.content.Intent
import android.net.Uri
import com.example.simplepomodor.ui.fragementFactory.SimpleFragmentFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        supportFragmentManager.fragmentFactory=SimpleFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //TODO KOnfigürasayon değişikliği oldugu zaman fazladan eklem olugu zaman nasıl tepki veriyor bakılıca
        if (savedInstanceState == null) {
            replaceFragment()
        }
        startDatabase()

        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data
    }


    private fun replaceFragment() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, PomodoroListFragment.newInstance()).commit()
        //transaction.addToBackStack("List").commit()

    }

    private fun startDatabase() {
        var pomodoroDatabase: PomodoroDatabase? = PomodoroDatabase.newInstance(applicationContext)
    }
}

