package com.example.idrd.presentation.cambiar_correo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.idrd.R
import com.example.idrd.domain.interactor.cambiar_correoInteractor.CambiarcorreoInteractorImpl
import com.example.idrd.presentation.cambiar_correo.CambiarCorreoContract
import com.example.idrd.presentation.cambiar_correo.cambiar_correoPresenter.CambiarCorreoPresenter
import kotlinx.android.synthetic.main.fragment_cambiarcorreo.*
import kotlinx.android.synthetic.main.fragment_cambiarcorreo.view.*


class FragmentCambiarcorreo : Fragment() , CambiarCorreoContract.CambiarCorreoView{

    lateinit var presenter: CambiarCorreoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View=  inflater!!.inflate(R.layout.fragment_cambiarcorreo, container, false)

        presenter= CambiarCorreoPresenter(CambiarcorreoInteractorImpl())
        presenter.attachView(this)

        view.botoncambiarcorreo.setOnClickListener {
            cambiarcorreo()
        }



        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        botoncambiarcorreo.visibility=View.GONE
        progressBar_cambiar_correo.visibility= View.VISIBLE
    }

    override fun hideProgressDialog() {
        progressBar_cambiar_correo.visibility=View.GONE
        botoncambiarcorreo.visibility=View.VISIBLE
    }

    override fun showSuccess() {
        Toast.makeText(context, "Correo actualizado correctamente", Toast.LENGTH_SHORT).show()
    }

    override fun cambiarcorreo() {

        val correo:String = cambiarcorreo.editText?.text.toString().trim()

        if (correo.isEmpty()){
            cambiarcorreo.error="Ingrese correo electronico"
            return
        }else{
            presenter.cambiarcorreo(correo)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}