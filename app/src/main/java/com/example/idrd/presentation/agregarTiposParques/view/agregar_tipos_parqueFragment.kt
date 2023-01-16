package com.example.idrd.presentation.agregarTiposParques.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.idrd.R
import com.example.idrd.data.model.TiposParque
import com.example.idrd.domain.interactor.crud_TiposParques.CrudTiposParquesInteractorImpl
import com.example.idrd.presentation.agregarTiposParques.AgregarTipoParqueContract
import com.example.idrd.presentation.agregarTiposParques.AgregarTipoParquePresenter.AgregarTipoParquePresenter
import kotlinx.android.synthetic.main.fragment_agregar_tipos_parque.*
import kotlinx.android.synthetic.main.fragment_agregar_tipos_parque.view.*


class agregar_tipos_parqueFragment : DialogFragment(), AgregarTipoParqueContract.AgregarTipoView {

    lateinit var presenter: AgregarTipoParquePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view:View=  inflater!!.inflate(R.layout.fragment_agregar_tipos_parque, container, false)

        presenter= AgregarTipoParquePresenter(CrudTiposParquesInteractorImpl())
        presenter.attachView(this)

        view.guardar_tipo_parque.setOnClickListener {
            addTipoParque()

        }
        view.cancelar_tipo_parque.setOnClickListener {
            cancelar()
        }


        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        guardar_tipo_parque.visibility=View.GONE
        cancelar_tipo_parque.visibility=View.GONE
        progressBar_guardar_tipo_parque.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        guardar_tipo_parque.visibility=View.VISIBLE
        cancelar_tipo_parque.visibility=View.VISIBLE
        progressBar_guardar_tipo_parque.visibility=View.GONE
    }

    override fun addTipoParque() {

        val tipo:String=txt_tipo_parque.editText?.text.toString().trim()

        if (presenter.checkEmptytipo(tipo)){
            txt_tipo_parque.error="Ingrese el tipo de parque"
            return
        }

        var tipo_parque=TiposParque()
        tipo_parque.tipo=tipo
        presenter.addTipoParque(tipo_parque)

    }

    override fun cancelar() {
       dismiss()
    }

    override fun showSuccess() {
        tipo_parque.setText("")
        Toast.makeText(context,"El tipo de parque guardado correctamente", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}