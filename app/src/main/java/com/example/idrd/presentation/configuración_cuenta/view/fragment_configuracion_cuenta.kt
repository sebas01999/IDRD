package com.example.idrd.presentation.configuración_cuenta.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.idrd.R
import com.example.idrd.presentation.cambiar_correo.view.fragment_cambiarcorreo
import com.example.idrd.presentation.cambiar_datospersonales.view.fragment_cambiardatosp
import kotlinx.android.synthetic.main.fragment_configuracion_cuenta.view.*
import kotlinx.android.synthetic.main.fragment_cuenta__admin_parque.view.*


class fragment_configuracion_cuenta : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View= inflater.inflate(R.layout.fragment_configuracion_cuenta, container, false)

        view.botoncambiarcorreo.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = fragment_cambiarcorreo()

            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        view.botoncambiardatosper.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = fragment_cambiardatosp()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }


        return view
    }



}