package com.example.idrd.presentation.cuenta.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.idrd.R
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.acceder_solicitudes.view.fragment_acceder_solicitudes
import com.example.idrd.presentation.configuración_cuenta.view.fragment_configuracion_cuenta
import kotlinx.android.synthetic.main.fragment_cuenta.view.*
import kotlinx.android.synthetic.main.fragment_cuenta__admin_parque.view.*


class cuentaFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view:View= inflater!!.inflate(R.layout.fragment_cuenta, container, false)
        if (arguments!=null){

            val user:Users= arguments?.getSerializable("user") as Users
            if (user!=null){
                view.nombreUser.text=user.nombre
                view.correoUser.text=user.correo
                view.direcUser.text=user.direction
            }
            view.botonSolicitudes.setOnClickListener {
                val solicitudes=fragment_acceder_solicitudes()
                solicitudes.arguments=arguments
                val transaction=fragmentManager?.beginTransaction()
                transaction?.replace(R.id.container, solicitudes)

                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }

            view.botonconfigUser.setOnClickListener {
                val transaction=fragmentManager?.beginTransaction()
                val fragmento = fragment_configuracion_cuenta()
                fragmento.arguments=arguments
                transaction?.replace(R.id.container, fragmento)

                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
        }



        return view
    }


}