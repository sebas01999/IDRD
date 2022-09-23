package com.example.idrd.presentation.cambiar_datospersonales.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.idrd.R
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.cambiar_datospInteractor.Cambiar_datospInteractorImpl
import com.example.idrd.presentation.cambiar_datospersonales.Cambiar_datospContract
import com.example.idrd.presentation.cambiar_datospersonales.cambiar_datospPresenter.Cambiar_datospPresenter
import kotlinx.android.synthetic.main.fragment_cambiardatosp.*
import kotlinx.android.synthetic.main.fragment_cambiardatosp.view.*


class fragment_cambiardatosp : Fragment(), Cambiar_datospContract.Cambiar_datospView {

    lateinit var presenter: Cambiar_datospPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_cambiardatosp, container, false)
        val user: Users = arguments?.getSerializable("user") as Users

        view.cctext.setText(user.cedula)
        view.nom_razonstext.setText(user.nombre)
        view.telefonotext.setText(user.telefono)
        view.directext.setText(user.direction)


        presenter= Cambiar_datospPresenter(Cambiar_datospInteractorImpl())
        presenter.attachView(this)

        view.botoncambiardatosp.setOnClickListener {
            cambiardatosp()
        }


        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
       botoncambiardatosp.visibility=View.GONE
        progressBar_cambiardatosper.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        progressBar_cambiardatosper.visibility=View.GONE
        botoncambiardatosp.visibility=View.VISIBLE
    }

    override fun showSuccess() {
        cctext.setText("")
        nom_razonstext.setText("")
        telefonotext.setText("")
        directext.setText("")

        Toast.makeText(context, "Datos personales actualizados correctamente", Toast.LENGTH_SHORT).show()
    }

    override fun cambiardatosp() {

        val cedula : String = cc.editText?.text.toString().trim()
        val nombre : String= nom_razons.editText?.text.toString().trim()
        val tel : String= telefono.editText?.text.toString().trim()
        val direccion: String = direc.editText?.text.toString().trim()

        if(presenter.checkcamposvacios(cedula)){
            cc.error="Ingrese su cedula"
            return
        }
        if(presenter.checkcamposvacios(nombre)){
            nom_razons.error="Ingrese su cedula"
            return
        }
        if(presenter.checkcamposvacios(tel)){
            telefono.error="Ingrese su cedula"
            return
        }
        if(presenter.checkcamposvacios(direccion)){
            direc.error="Ingrese su cedula"
            return
        }
        val user: Users = arguments?.getSerializable("user") as Users

        user.cedula=cedula
        user.nombre=nombre
        user.telefono=tel
        user.direction=direccion

        presenter.cambiardatosp(user)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }
}