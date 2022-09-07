package com.example.idrd.domain.interactor.recuperarConInteractor

interface RecuperarConInteractor {
    suspend fun recuperarCon(correo:String)
}