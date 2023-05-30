package com.example.idrd.presentation.info_mapa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.presentation.info_mapa.model.AforoViewModel
import kotlinx.android.synthetic.main.fragment_infomapa.*
import kotlinx.android.synthetic.main.fragment_infomapa.view.*


class FragmentInfomapa :  DialogFragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(AforoViewModel::class.java)}

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
        observeData()
        return view
    }

    fun observeData(){
        val parque: Parque = arguments?.getSerializable("parque") as Parque
        viewModel.fetchAforoDataRealTime(parque.id).observe(viewLifecycleOwner, Observer {
            aforomapa.text = it.size.toString() + " personas"
        })
    }
}