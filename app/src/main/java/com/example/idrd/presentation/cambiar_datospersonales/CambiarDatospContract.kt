package com.example.idrd.presentation.cambiar_datospersonales

import com.example.idrd.data.model.Users

interface CambiarDatospContract {

    interface CambiarDatospView {

        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun cambiardatosp()

    }
    interface CambiarDatospPresenter {

        fun attachView(view: CambiarDatospView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun cambiardatosp(datosp:Users)
        fun checkcamposvacios(campo:String):Boolean
    }
}