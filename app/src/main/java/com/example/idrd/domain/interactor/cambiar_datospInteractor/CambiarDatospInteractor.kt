package com.example.idrd.domain.interactor.cambiar_datospInteractor

import com.example.idrd.data.model.Users

interface CambiarDatospInteractor {

    suspend fun cambiarDatosp(datosp:Users)

}