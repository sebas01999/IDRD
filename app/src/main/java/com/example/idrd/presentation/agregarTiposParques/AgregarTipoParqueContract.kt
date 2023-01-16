package com.example.idrd.presentation.agregarTiposParques

import com.example.idrd.data.model.TiposParque
import com.example.idrd.presentation.agregarParque.AgregarParqueContract

interface AgregarTipoParqueContract {

    interface AgregarTipoView {
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun addTipoParque()
        fun cancelar()
        fun showSuccess()
    }

    interface AgregarTipoPresenter{
        fun attachView(view:AgregarTipoView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun addTipoParque(tipo_parque:TiposParque)
        fun checkEmptytipo(tipo: String):Boolean

    }


}