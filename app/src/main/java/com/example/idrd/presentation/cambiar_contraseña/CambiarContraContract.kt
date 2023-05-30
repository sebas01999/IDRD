package com.example.idrd.presentation.cambiar_contraseña

interface CambiarContraContract {

    interface CambiarContraView{

        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun cambiarcontra()

    }

    interface CambiarContraPresenter{

        fun attachView(view:CambiarContraView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun cambiarcontra()


    }
}