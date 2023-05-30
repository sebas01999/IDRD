package com.example.idrd.presentation.notificaciones

import com.example.idrd.data.model.Notificacion

interface NotificacionesContract {

    interface NotificacionesView {

    }
    interface NotificacionesPresenter{

        fun attachView(view: NotificacionesView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun notificacion(notificacion:Notificacion, id:String)
    }

}