package com.example.idrd.domain.interactor.aceptarrechazarInteractor

interface AceptarrechazarInteractor {

    suspend fun editsolicitud(estadosol:String, idsol:String)


}