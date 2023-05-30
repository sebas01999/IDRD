package com.example.idrd.domain.interactor.calificacionInteractor

import com.example.idrd.data.model.Calificacion

interface CalificacionInteractor {
    suspend fun sendCalificacion(calificacion: Calificacion)
    suspend fun editCalificacion(calificacion: Calificacion)
    suspend fun editCalificacionParque(calificacion: String, idParque:String)
}