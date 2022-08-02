package com.example.idrd.presentation.info_mapa.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import kotlinx.android.synthetic.main.fragment_infomapa.view.*


class Fragment_infomapa :  DialogFragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view: View= inflater!!.inflate(R.layout.fragment_infomapa, container, false)

        if(arguments!=null){

            val parque: Parque = arguments?.getSerializable("parque") as Parque

            view.direcmapa.text=parque.ubicacion
            view.horamapa.text=parque.horario
            view.nommapa.text=parque.nombre

        }
        return view
    }


}