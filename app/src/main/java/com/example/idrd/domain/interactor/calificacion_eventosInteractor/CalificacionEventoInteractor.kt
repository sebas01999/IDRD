package com.example.idrd.domain.interactor.calificacion_eventosInteractor

import com.example.idrd.data.model.CalificacionEvento

interface CalificacionEventoInteractor {

    suspend fun sendCalificacionEvento(calificacionEvento:CalificacionEvento)
    suspend fun EditCalificacionEvento(calificaciónEvento:CalificacionEvento)
    suspend fun EditCalificacionEventoGeneral(calificaciónEvento: String, idEvento: String)

}