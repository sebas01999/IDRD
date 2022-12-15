package com.example.idrd.presentation.notificaciones.view

import com.example.idrd.data.model.Notificacion

interface Notificaciones_Contract {

    interface NotificacionesView {

    }
    interface NotificacionesPresenter{

        fun attachView(view: NotificacionesView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun notificacion(notificacion:Notificacion,Id:String)
    }

}