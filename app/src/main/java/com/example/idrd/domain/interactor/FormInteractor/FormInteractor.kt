package com.example.idrd.domain.interactor.FormInteractor

import com.example.idrd.data.model.Solicitud


interface FormInteractor {
    suspend fun sendRequest(solicitud: Solicitud)
    suspend fun deleteRequest(solicitud: Solicitud)
    suspend fun editRequest(solicitud: Solicitud)
}