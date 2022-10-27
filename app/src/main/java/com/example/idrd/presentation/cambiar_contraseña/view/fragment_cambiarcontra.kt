package com.example.idrd.presentation.cambiar_contraseña.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.idrd.R
import com.example.idrd.domain.interactor.cambiarcontraInteractor.CambiarcontraInteractorImpl
import com.example.idrd.presentation.cambiar_contraseña.Cambiar_contraContract
import com.example.idrd.presentation.cambiar_contraseña.cambiar_contraseñaPresenter.Cambiar_contraseñaPresenter
import kotlinx.android.synthetic.main.fragment_cambiarcontra.*
import kotlinx.android.synthetic.main.fragment_cambiarcontra.view.*


class fragment_cambiarcontra : Fragment() , Cambiar_contraContract.Cambiar_contraView{

    lateinit var presenter: Cambiar_contraseñaPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View=  inflater.inflate(R.layout.fragment_cambiarcontra, container, false)

        presenter= Cambiar_contraseñaPresenter(CambiarcontraInteractorImpl())
        presenter.attachView(this)

        view.cambiarcontra.setOnClickListener {
            cambiarcontraseña()
        }
        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        cambiarcontra.visibility= View.GONE
        progressBar_cambiar_contra.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        progressBar_cambiar_contra.visibility=View.GONE
        cambiarcontra.visibility= View.VISIBLE
    }

    override fun showSuccess() {
        Toast.makeText(context, "El correo ha sido enviado", Toast.LENGTH_SHORT).show()
    }

    override fun cambiarcontraseña() {
        presenter.cambiarcontraseña()
    }


}