package com.example.idrd.presentation.acceder_solicitudes

import com.example.idrd.data.model.Solicitud


interface Acceder_solicitudesContract {
    interface Acceder_solicitudesView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun borrarSolicitud(solicitud: Solicitud)
    }
    interface Acceder_solicitudesPresenter{
        fun attachView(view: Acceder_solicitudesView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun borrarSolicitud(solicitud: Solicitud)
    }
}