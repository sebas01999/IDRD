package com.example.idrd.presentation.cuenta_Director.view

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
import com.example.idrd.presentation.configuración_cuenta.view.FragmentConfiguracionCuenta
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.example.idrd.presentation.notificaciones.view.NotificacionesFragment
import com.example.idrd.presentation.webView.view.WebViewFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cuenta_director.*
import kotlinx.android.synthetic.main.fragment_cuenta_director.view.*


class CuentaDirectorFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(NotificacionesViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_cuenta_director, container, false)
            if (arguments!=null){
                val user: Users = arguments?.getSerializable("user") as Users
                if (user!=null){
                    view.nombreDirec.text=user.nombre
                    view.correoDirec.text=user.correo
                    view.direcDirec.text=user.direction
                }
                view.botonReportesDirec.setOnClickListener {
                    val transaction=fragmentManager?.beginTransaction()
                    val fragmento = WebViewFragment()
                    fragmento.arguments=arguments
                    BaseCod().transactionFragmet(transaction!!,fragmento)
                }
                view.botonconfigDirec.setOnClickListener {
                    val transaction=fragmentManager?.beginTransaction()
                    val fragmento = FragmentConfiguracionCuenta()
                    fragmento.arguments=arguments
                    BaseCod().transactionFragmet(transaction!!,fragmento)
                }
                view.botonnotificacionesDirec.setOnClickListener {
                    val transaction=fragmentManager?.beginTransaction()
                    val fragmento = NotificacionesFragment()
                    fragmento.arguments=arguments
                    BaseCod().transactionFragmet(transaction!!,fragmento)
                }
                view.cerrarsesionDirec.setOnClickListener {
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
                    badgeDr.clear()
                }else{
                    badgeDr.setNumber(numer)
                }

            }
        })
    }
}