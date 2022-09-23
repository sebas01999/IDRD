package com.example.idrd.presentation.aceptar_rechazar

interface Aceptar_rechazarContract {

    interface Aceptar_rechazarView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun editarsolicitud()
    }

    interface Aceptar_rechazarPresenter{

        fun attachView(view:Aceptar_rechazarView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun editarsolicitud( estadosol: String, idsol: String)
    }

}