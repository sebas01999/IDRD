package com.example.idrd.domain.interactor.aceptarrechazarInteractor

interface AceptarrechazarInteractor {

    suspend fun Editsolicitud(estadosol:String, idsol:String)


}