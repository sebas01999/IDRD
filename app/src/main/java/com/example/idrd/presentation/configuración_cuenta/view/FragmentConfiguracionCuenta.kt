package com.example.idrd.presentation.configuración_cuenta.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.idrd.R
import com.example.idrd.base.BaseCod
import com.example.idrd.presentation.cambiar_contraseña.view.FragmentCambiarcontra
import com.example.idrd.presentation.cambiar_correo.view.FragmentCambiarcorreo
import com.example.idrd.presentation.cambiar_datospersonales.view.FragmentCambiardatosp
import kotlinx.android.synthetic.main.fragment_configuracion_cuenta.view.*


class FragmentConfiguracionCuenta : Fragment() {

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
            val fragmento = FragmentCambiarcorreo()
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.botoncambiardatosper.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = FragmentCambiardatosp()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.botoncambiarcontraseña.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = FragmentCambiarcontra()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }


        return view
    }



}