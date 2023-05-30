package com.example.idrd.presentation.cuenta.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.base.BaseCod
import com.example.idrd.data.database.RepoUser
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.acceder_solicitudes.view.FragmentAccederSolicitudes
import com.example.idrd.presentation.configuración_cuenta.view.FragmentConfiguracionCuenta
import com.example.idrd.presentation.contacto.ContactoFragment
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.example.idrd.presentation.notificaciones.view.NotificacionesFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cuenta.*
import kotlinx.android.synthetic.main.fragment_cuenta.view.*


class CuentaFragment : Fragment() {
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
                val solicitudes=FragmentAccederSolicitudes()
                solicitudes.arguments=arguments
                val transaction=fragmentManager?.beginTransaction()
                BaseCod().transactionFragmet(transaction!!,solicitudes)
            }

            view.botonconfigUser.setOnClickListener {
                val transaction=fragmentManager?.beginTransaction()
                val fragmento = FragmentConfiguracionCuenta()
                fragmento.arguments=arguments
                BaseCod().transactionFragmet(transaction!!,fragmento)
            }

            view.botonnotificacionesUser.setOnClickListener {
                val transaction=fragmentManager?.beginTransaction()
                val fragmento = NotificacionesFragment()
                fragmento.arguments=arguments
                BaseCod().transactionFragmet(transaction!!,fragmento)
            }
            view.ayudaUser.setOnClickListener {
                val transaction=fragmentManager?.beginTransaction()
                val fragmento = ContactoFragment()
                BaseCod().transactionFragmet(transaction!!,fragmento)
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