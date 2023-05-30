package com.example.idrd.presentation.aceptar_rechazar

interface AceptarRechazarContract {

    interface AceptarRechazarView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun editarsolicitud()
    }

    interface AceptarRechazarPresenter{
        fun attachView(view:AceptarRechazarView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun editarsolicitud( estadosol: String, idsol: String)
    }

}