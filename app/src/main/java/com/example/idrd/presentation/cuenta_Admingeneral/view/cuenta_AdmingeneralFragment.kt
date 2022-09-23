package com.example.idrd.presentation.cuenta_Admingeneral.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.idrd.R
import com.example.idrd.presentation.configuración_cuenta.view.fragment_configuracion_cuenta
import com.example.idrd.presentation.crud_parques.view.CrudParquesFragment
import com.example.idrd.presentation.descripcion.view.descripcionFragment
import kotlinx.android.synthetic.main.fragment_cuenta__admin_parque.view.*
import kotlinx.android.synthetic.main.fragment_cuenta__admingeneral.view.*

class cuenta_AdmingeneralFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_cuenta__admingeneral, container, false)
        view.botonParquesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = CrudParquesFragment()
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        view.botonconfigadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = fragment_configuracion_cuenta()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return view
    }


}