package com.example.idrd.domain.interactor.notificaciones_Interactor

import com.example.idrd.data.model.Notificacion

interface NotificacionesInteractor {

    suspend fun notificacion(notificacion: Notificacion, id:String)

}
