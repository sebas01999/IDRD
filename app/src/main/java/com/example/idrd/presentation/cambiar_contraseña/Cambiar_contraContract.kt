package com.example.idrd.presentation.cambiar_contraseña

interface Cambiar_contraContract {

    interface Cambiar_contraView{

        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun cambiarcontraseña()

    }

    interface Cambiar_contraPresenter{

        fun attachView(view:Cambiar_contraView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun cambiarcontraseña(contranueva:String)
        fun checkemptyfields(campo:String ) : Boolean
        fun valcontra(contra:String) : Boolean
        fun valcontrarep(repcontra: String, contra: String) : Boolean

    }
}