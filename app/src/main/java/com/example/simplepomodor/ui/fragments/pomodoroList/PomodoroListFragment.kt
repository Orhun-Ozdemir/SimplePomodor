package com.example.simplepomodor.ui.fragments.pomodoroList

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplepomodor.R
import com.example.simplepomodor.adapter.PomodoroListAdapter
import com.example.simplepomodor.model.Pomodoro
import com.example.simplepomodor.model.PomodoroCircle
import com.example.simplepomodor.model.modelEnum.PomodoroCircleStatus
import com.example.simplepomodor.repositories.database.PomodoroDatabase
import com.example.simplepomodor.ui.fragments.pomodore.PomodoreFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PomodoroListFragment : Fragment(),LifecycleOwner {


    private lateinit var fab: FloatingActionButton
    private lateinit var rv: RecyclerView
    private lateinit var adapter: PomodoroListAdapter
    private lateinit var db: PomodoroDatabase


    /*

    onCreate()
    onCreateView()
    onViewCreated()
    onStart()
    onResume()
    onPause()
    onStop()
    onDestroyView()
    onDestroy()
     */

    override fun onAttach(context: Context) {
        Log.d("EVENT","ATTACH");
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            db = activity?.let { it1 -> PomodoroDatabase.newInstance(it1.applicationContext) }!!
            adapter = PomodoroListAdapter(db.pomodoroDao().getPomodoroList(), parentFragmentManager)

        }
        Log.d("EVENT","ON_CREATE");
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("EVENT","ON_CREATE_VIEW");
        val view: View = inflater.inflate(R.layout.fragment_pomodor_list, container, false)

        fab = view.findViewById(R.id.fab_add_pomodoro)
        rv = view.findViewById(R.id.rv_pomodoro_list)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter

        fab.setOnClickListener {

            // replaceFragment()
            showAddPomodoroDialog()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("EVENT","ON_VIEW_CREATED");
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        Log.d("EVENT","ON_START");
        super.onStart()
    }

    override fun onResume() {
        Log.d("EVENT","RESUME");
        super.onResume()
    }

    override fun onPause() {
        Log.d("EVENT","PAUSE");
        super.onPause()
    }

    override fun onStop() {
        Log.d("EVENT","STOP");
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("EVENT","ON_DESTROY_VIEW");
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("EVENT","ON_DESTROY");
        super.onDestroy()
    }
    override fun onDetach() {
        Log.d("EVENT","ON_DETACH");
        super.onDetach()
    }


    private fun replaceFragment() {

        var fragmentManager: FragmentManager = parentFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frame, PomodoreFragment.newInstance(), "Create").commit()
        fragmentTransaction.addToBackStack("Create")

    }


    private fun showAddPomodoroDialog() {

        var etTask: EditText
        var etCount: EditText
        var btnAdd: Button
        var dialog: Dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_pomodoro)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        etTask = dialog.findViewById(R.id.et_dialog_pomodoro_task)
        etCount = dialog.findViewById(R.id.et_dialog_pomodoro_count)
        btnAdd = dialog.findViewById(R.id.btn_dialog_add)

        btnAdd.setOnClickListener {
            if (etTask.text.isNotEmpty() && etCount.text.isNotEmpty()) {

                var pomodoro: Pomodoro = Pomodoro(
                    0,
                    etTask.text.toString(),
                    etCount.text.toString().toInt(),
                    0,
                    false,
                    "",
                    "",
                    createEmptyPomodoroCircle(etCount.text.toString().toInt())
                )
                db.pomodoroDao().addPomodoro(pomodoro)
                dialog.dismiss()
                getPomodoro()
            } else {
                dialog.dismiss()

            }


        }
        dialog.show()


    }

    private fun createEmptyPomodoroCircle(count: Int): ArrayList<PomodoroCircle> {

        var arrayList = ArrayList<PomodoroCircle>()
        for (i in 1..count) {
            arrayList.add(PomodoroCircle(PomodoroCircleStatus.EMPTY))


        }

        return arrayList
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PomodoroListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun getPomodoro() {

        db = activity?.let { it1 -> PomodoroDatabase.newInstance(it1.applicationContext) }!!
        adapter = PomodoroListAdapter(db.pomodoroDao().getPomodoroList(), parentFragmentManager)
        rv.adapter = adapter


    }


}