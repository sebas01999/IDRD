package com.example.idrd.presentation.mainprincipal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.base.MyApp
import com.example.idrd.presentation.codigo_QR.view.CodigoQRFragment
import com.example.idrd.presentation.cuenta.view.CuentaFragment
import com.example.idrd.presentation.cuenta_AdminParque.view.CuentaAdminParqueFragment
import com.example.idrd.presentation.cuenta_Admingeneral.view.CuentaAdmingeneralFragment
import com.example.idrd.presentation.cuenta_Director.view.CuentaDirectorFragment
import com.example.idrd.presentation.eventos.view.EventosFragment
import com.example.idrd.presentation.inicio.view.InicioFragment
import com.example.idrd.presentation.mainprincipal.model.UserIDViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_mainprincipal.*

class Mainprincipal : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(UserIDViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainprincipal)
        replaceFragment(InicioFragment())
        btn_nav.setItemSelected(R.id.item1)
        observerData()
        btn_nav.setOnItemSelectedListener { es ->
            when(es){
                R.id.item1 -> replaceFragment(InicioFragment())
                R.id.item2 -> replaceFragment(CodigoQRFragment())
                R.id.item3 -> replaceFragment(EventosFragment())
                R.id.item4 -> moveCuenta()
            }
        }
    }
    fun observerData(){
        val auth =FirebaseAuth.getInstance()
        viewModel.fetchDataIDUser(auth.currentUser?.uid.toString()).observe(this, Observer {
            val myApp= application as MyApp
            myApp.user=it.get(0)
        })
    }
    fun moveCuenta(){
        val myApp= application as MyApp

        val bundle=Bundle()
        bundle.putSerializable("user", myApp.user)
        val adminParque=CuentaAdminParqueFragment()
        val cuentaUser=CuentaFragment()
        val adming=CuentaAdmingeneralFragment()
        val director = CuentaDirectorFragment()
        adminParque.arguments=bundle
        cuentaUser.arguments=bundle
        adming.arguments=bundle
        if (myApp.user.rol.equals("USER")){
            replaceFragment(cuentaUser)
        }else if(myApp.user.rol.equals("ADMING")){
            replaceFragment(adming)
        }else if (myApp.user.rol.equals("DIRECTOR")){
            replaceFragment(director)
        } else{
            replaceFragment(adminParque)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment!=null){
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
    }
}