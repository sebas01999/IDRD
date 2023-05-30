package com.example.idrd.presentation.cuenta_Admingeneral.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.base.BaseActivity
import com.example.idrd.base.BaseCod
import com.example.idrd.data.database.RepoUser
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.configuración_cuenta.view.FragmentConfiguracionCuenta
import com.example.idrd.presentation.contacto.ContactoFragment
import com.example.idrd.presentation.crud_parques.view.CrudParquesFragment
import com.example.idrd.presentation.crud_tipos_parques.view.CrudTiposFragment
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.example.idrd.presentation.notificaciones.view.NotificacionesFragment
import com.example.idrd.presentation.webView.view.WebViewFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cuenta__admingeneral.*
import kotlinx.android.synthetic.main.fragment_cuenta__admingeneral.view.*

class CuentaAdmingeneralFragment : Fragment() {
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
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.cerrarsesionAg.setOnClickListener {
            signOut()
        }
        view.botonTiposParquesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = CrudTiposFragment()
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.botonconfigadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = FragmentConfiguracionCuenta()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.ayudaadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = ContactoFragment()
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.botonnotificacionesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = NotificacionesFragment()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }

        view.botonreportesadming.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = WebViewFragment()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
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