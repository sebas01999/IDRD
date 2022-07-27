package com.example.idrd.presentation.mainprincipal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.idrd.R
import com.example.idrd.presentation.acceder_solicitudes.view.fragment_acceder_solicitudes
import com.example.idrd.presentation.crud_parques.view.CrudParquesFragment
import com.example.idrd.presentation.cuenta.view.cuentaFragment
import com.example.idrd.presentation.cuenta_AdminParque.view.cuenta_AdminParqueFragment
import com.example.idrd.presentation.cuenta_Admingeneral.view.cuenta_AdmingeneralFragment
import com.example.idrd.presentation.eventos.view.eventosFragment
import com.example.idrd.presentation.ingresar.view.ingresarFragment
import com.example.idrd.presentation.inicio.view.InicioFragment
import com.example.idrd.presentation.mapa.view.MapaFragment
import kotlinx.android.synthetic.main.activity_mainprincipal.*

class mainprincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainprincipal)
        replaceFragment(InicioFragment())
        btn_nav.setItemSelected(R.id.item1)

        btn_nav.setOnItemSelectedListener { es ->
            when(es){
                R.id.item1 -> replaceFragment(InicioFragment())
                R.id.item2 -> replaceFragment(MapaFragment ())
                R.id.item3 -> replaceFragment(eventosFragment())
                R.id.item4 -> replaceFragment(cuenta_AdmingeneralFragment())
            }
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