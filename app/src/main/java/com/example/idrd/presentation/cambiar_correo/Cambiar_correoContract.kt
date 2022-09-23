package com.example.idrd.presentation.cambiar_correo

import com.example.idrd.presentation.aceptar_rechazar.Aceptar_rechazarContract

interface Cambiar_correoContract {

    interface Cambiar_correoView {

        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun cambiarcorreo()
    }
    interface Cambiar_correoPresenter{

        fun attachView(view: Cambiar_correoView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun cambiarcorreo(correo:String)

    }

}