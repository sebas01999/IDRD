package com.example.idrd.domain.interactor.crud_TiposParques

import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.TiposParque

interface CrudTiposParquesInteractor {

    suspend fun addTipoParque(tipoParque : TiposParque)
    suspend fun editTipoParque(tipoParque : TiposParque)
    suspend fun borarTipoParque(tipoParque : TiposParque)

}