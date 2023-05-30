package com.example.idrd.domain.interactor.calificacion_eventosInteractor

import com.example.idrd.data.model.CalificacionEvento

interface CalificacionEventoInteractor {

    suspend fun sendCalificacionEvento(calificacionEvento:CalificacionEvento)
    suspend fun editCalificacionEvento(calificacionEvento:CalificacionEvento)
    suspend fun editCalificacionEventoGeneral(calificacionEvento: String, idEvento: String)

}