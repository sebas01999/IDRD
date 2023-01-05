package com.example.idrd.presentation.cuenta_AdminParque.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.acceder_solicitudes.view.fragment_acceder_solicitudes
import com.example.idrd.presentation.configuración_cuenta.view.fragment_configuracion_cuenta
import com.example.idrd.presentation.crud_eventos.view.crudEventosFragment
import com.example.idrd.presentation.crud_parques.view.CrudParquesFragment
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.example.idrd.presentation.notificaciones.view.NotificacionesFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cuenta__admin_parque.view.*
import kotlinx.android.synthetic.main.fragment_cuenta__admingeneral.*


class cuenta_AdminParqueFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(NotificacionesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_cuenta__admin_parque, container, false)
        if (arguments!=null){
            val user: Users = arguments?.getSerializable("user") as Users
            view.nombreadminP.text=user.nombre
            view.correoadminP.text=user.correo
            view.direcadminP.text=user.direction
        }
        view.botonEventos.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = crudEventosFragment()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        view.botonconfigadminP.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = fragment_configuracion_cuenta()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        view.botonSolicitudesAdminP.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = fragment_acceder_solicitudes()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        view.botonnotificacionesadminP.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = NotificacionesFragment()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        observeData()
        return view
    }

    fun observeData(){
        val auth= FirebaseAuth.getInstance().currentUser?.uid.toString()
        viewModel.fetchNotificacionesData(auth).observe(viewLifecycleOwner, Observer {
            if (!it.isEmpty()){
                val numer =it.count { noti->
                    noti.visto==false
                }
                if (numer==0){
                    badge.clear()
                }else{
                    badge.setNumber(numer)
                }

            }
        })
    }
}