package com.example.idrd.domain.interactor.crudParques

import android.net.Uri
import com.example.idrd.data.model.Parque

interface CrudParqueInteractor {
    suspend fun addParque(parque: Parque, uri: Uri )
    suspend fun editParque(parque: Parque)
    suspend fun editParqueFoto(parque: Parque , uri: Uri)
    suspend fun borarParque(parque: Parque)
}