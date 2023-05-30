package com.example.idrd.domain.interactor.registroEntradaInteractor

import com.example.idrd.data.model.Aforo

interface RegistroEntradaInteractor {

    suspend fun registroAforo(aforo: Aforo)

}