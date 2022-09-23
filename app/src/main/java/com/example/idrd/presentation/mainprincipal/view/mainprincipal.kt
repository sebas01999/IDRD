package com.example.idrd.presentation.mainprincipal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.presentation.codigo_QR.view.codigoQRFragment
import com.example.idrd.presentation.cuenta.view.cuentaFragment
import com.example.idrd.presentation.cuenta_AdminParque.view.cuenta_AdminParqueFragment
import com.example.idrd.presentation.cuenta_Admingeneral.view.cuenta_AdmingeneralFragment
import com.example.idrd.presentation.eventos.view.eventosFragment
import com.example.idrd.presentation.inicio.view.InicioFragment
import com.example.idrd.presentation.mainprincipal.model.UserIDViewModel
import com.example.idrd.presentation.mapa.view.MapsFragment
import com.example.idrd.presentation.registro_botones.view.fragment_registro_botones
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_mainprincipal.*

class mainprincipal : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(UserIDViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainprincipal)
        replaceFragment(InicioFragment())
        btn_nav.setItemSelected(R.id.item1)

        btn_nav.setOnItemSelectedListener { es ->
            when(es){
                R.id.item1 -> replaceFragment(InicioFragment())
                R.id.item2 -> observerDataregistro()
                R.id.item3 -> replaceFragment(eventosFragment())
                R.id.item4 -> observerData()
            }
        }
    }
    fun observerData(){
        val auth =FirebaseAuth.getInstance()
        viewModel.fetchDataIDUser(auth.currentUser?.uid.toString()).observe(this, Observer {
            val bundle=Bundle()
            bundle.putSerializable("user", it.get(0))
            val adminParque=cuenta_AdminParqueFragment()
            val cuentaUser=cuentaFragment()
            val adming=cuenta_AdmingeneralFragment()
            adminParque.arguments=bundle
            cuentaUser.arguments=bundle
            adming.arguments=bundle
            if (it.get(0).rol.equals("USER")){
               replaceFragment(cuentaUser)
            }else if(it.get(0).rol.equals("ADMING")){
                replaceFragment(adming)
            }else{
                replaceFragment(adminParque)
            }
        })

    }
    fun observerDataregistro(){
        val auth =FirebaseAuth.getInstance()
        viewModel.fetchDataIDUser(auth.currentUser?.uid.toString()).observe(this, Observer {
            val bundle=Bundle()
            bundle.putSerializable("user", it.get(0))
            val adminParque=fragment_registro_botones()
            val user=codigoQRFragment()

            adminParque.arguments=bundle
            user.arguments=bundle
            if (it.get(0).rol.equals("USER")){
                replaceFragment(user)
            }else if(it.get(0).rol.equals("ADMING")){
                replaceFragment(user)
            }else{
                replaceFragment(adminParque)
            }
        })

    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment!=null){
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
    }
}