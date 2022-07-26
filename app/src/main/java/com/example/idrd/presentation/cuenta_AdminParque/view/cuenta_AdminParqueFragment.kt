package com.example.idrd.presentation.cuenta_AdminParque.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.idrd.R
import com.example.idrd.presentation.crud_eventos.view.crudEventosFragment
import com.example.idrd.presentation.crud_parques.view.CrudParquesFragment
import kotlinx.android.synthetic.main.fragment_cuenta__admin_parque.view.*


class cuenta_AdminParqueFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_cuenta__admin_parque, container, false)
        view.botonEventos.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = crudEventosFragment()
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        return view
    }


}