package com.example.idrd.presentation.cambiar_correo

interface CambiarCorreoContract {

    interface CambiarCorreoView {

        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun cambiarcorreo()
    }
    interface CambiarCorreoPresenter{

        fun attachView(view: CambiarCorreoView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun cambiarcorreo(correo:String)

    }

}