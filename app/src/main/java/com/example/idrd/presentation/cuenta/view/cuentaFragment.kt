package com.example.idrd.presentation.cuenta.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.database.RepoUser
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.acceder_solicitudes.view.fragment_acceder_solicitudes
import com.example.idrd.presentation.configuración_cuenta.view.fragment_configuracion_cuenta
import com.example.idrd.presentation.main.MainActivity
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.example.idrd.presentation.notificaciones.view.NotificacionesFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cuenta.*
import kotlinx.android.synthetic.main.fragment_cuenta.view.*


class cuentaFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(NotificacionesViewModel::class.java) }

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

            view.botonnotificacionesUser.setOnClickListener {
                val transaction=fragmentManager?.beginTransaction()
                val fragmento = NotificacionesFragment()
                fragmento.arguments=arguments
                transaction?.replace(R.id.container, fragmento)

                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
            view.cerrarsesionUs.setOnClickListener {
                signOut()
            }
        }
        observeData()


        return view
    }
    private fun signOut() {
        activity?.let { RepoUser().signOut(it) }
    }
    fun observeData(){
        val auth= FirebaseAuth.getInstance().currentUser?.uid.toString()
        viewModel.fetchNotificacionesData(auth).observe(viewLifecycleOwner, Observer {
            if (!it.isEmpty()){
                val numer =it.count { noti->
                    noti.visto==false
                }
                if (numer==0){
                    badgeUs.clear()
                }else{
                    badgeUs.setNumber(numer)
                }

            }
        })
    }

}