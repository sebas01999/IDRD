package com.example.idrd.presentation.cuenta_Admingeneral.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.database.RepoUser
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.configuración_cuenta.view.fragment_configuracion_cuenta
import com.example.idrd.presentation.crud_parques.view.CrudParquesFragment
import com.example.idrd.presentation.crud_tipos_parques.view.CrudTiposFragment
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.example.idrd.presentation.notificaciones.view.NotificacionesFragment
import com.example.idrd.presentation.webView.view.WebViewFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cuenta__admingeneral.*
import kotlinx.android.synthetic.main.fragment_cuenta__admingeneral.view.*

class cuenta_AdmingeneralFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(NotificacionesViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_cuenta__admingeneral, container, false)
        if (arguments!=null){
            val user: Users = arguments?.getSerializable("user") as Users
            view.nombreadming.text=user.nombre
            view.correoadming.text=user.correo
            view.direcadmingv.text=user.direction
        }
        view.botonParquesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = CrudParquesFragment()
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        view.cerrarsesionAg.setOnClickListener {
            signOut()
        }
        view.botonTiposParquesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = CrudTiposFragment()
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

        view.botonnotificacionesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = NotificacionesFragment()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        view.botonreportesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = WebViewFragment()
            fragmento.arguments=arguments
            transaction?.replace(R.id.container, fragmento)

            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()
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
                    badgeAg.clear()
                }else{
                    badgeAg.setNumber(numer)
                }

            }
        })
    }

}