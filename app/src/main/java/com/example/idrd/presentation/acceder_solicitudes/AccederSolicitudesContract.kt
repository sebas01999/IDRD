package com.example.idrd.presentation.acceder_solicitudes

import com.example.idrd.data.model.Solicitud


interface AccederSolicitudesContract {
    interface AccederSolicitudesView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun borrarSolicitud(solicitud: Solicitud)
    }
    interface AccederSolicitudesPresenter{
        fun attachView(view: AccederSolicitudesView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun borrarSolicitud(solicitud: Solicitud)
    }
}