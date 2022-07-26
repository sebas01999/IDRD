package com.example.idrd.domain.interactor.crudEventos

import android.net.Uri
import com.example.idrd.data.model.Evento

interface CrudEventoInteractor {
    suspend fun addEventos(evento: Evento, uri: Uri)
    suspend fun editEvento(evento: Evento)
    suspend fun editEventoFoto(evento: Evento, uri: Uri)
    suspend fun borarEvento(evento: Evento)
}