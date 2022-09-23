package com.example.idrd.domain.interactor.cambiar_datospInteractor

import com.example.idrd.data.model.Users

interface Cambiar_datospInteractor {

    suspend fun cambiar_datosp(datosp:Users)

}