package com.example.idrd.presentation.recuperar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.idrd.R
import com.example.idrd.domain.interactor.recuperarConInteractor.RecuperarConInteractorImpl
import com.example.idrd.presentation.recuperar.RecuperarContract
import com.example.idrd.presentation.recuperar.recuperarPresenter.RecuperarPresenter
import kotlinx.android.synthetic.main.fragment_crud_tipos.*
import kotlinx.android.synthetic.main.fragment_recuperar.*
import kotlinx.android.synthetic.main.fragment_recuperar.view.*


class fragment_recuperar : Fragment(), RecuperarContract.RecuperarView {

    lateinit var presenter: RecuperarPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_recuperar, container, false)
        presenter= RecuperarPresenter(RecuperarConInteractorImpl())
        presenter.attachView(this)

        view.buttonEnviar.setOnClickListener {
            sendEmail()
        }

        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        buttonEnviar.visibility=View.GONE
        progressBar_recuperar.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        buttonEnviar.visibility=View.VISIBLE
        progressBar_recuperar.visibility=View.GONE
    }

    override fun sendEmail() {
        val correo:String=editTextEmail.editText?.text.toString().trim()

        if (presenter.checkEmptyTipo(correo)){
            editTextEmail.error="Ingrese un correo"
            return
        }
        presenter.sendEmail(correo)
    }

    override fun showSuccess(msgSuccess: String?) {
        Toast.makeText(context, msgSuccess, Toast.LENGTH_SHORT).show()
    }


}