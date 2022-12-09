package com.example.idrd.domain.interactor.notificaciones_Interactor

import com.example.idrd.data.model.Notificacion

interface Notificaciones_Interactor {

    suspend fun notificacion(notificacion: Notificacion, Id:String)

}
