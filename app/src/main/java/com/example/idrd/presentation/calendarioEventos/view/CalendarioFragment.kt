package com.example.idrd.presentation.calendarioEventos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import kotlinx.android.synthetic.main.fragment_calendario.*
import java.text.SimpleDateFormat


class CalendarioFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_calendario, container, false)

        observeData()



        return view
    }



    fun observeData(){

    }
}







