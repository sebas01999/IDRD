package com.example.idrd.domain.interactor.crud_TiposParques

import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.TiposParque

interface CrudTiposParquesInteractor {

    suspend fun addTipoParque(tipo_parque : TiposParque)
    suspend fun editTipoParque(tipo_parque : TiposParque)
    suspend fun borarTipoParque(tipo_parque : TiposParque)

}