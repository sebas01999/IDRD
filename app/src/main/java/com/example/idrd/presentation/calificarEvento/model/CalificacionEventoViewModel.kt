package com.example.idrd.presentation.calificarEvento.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoCalificacionesEventos
import com.example.idrd.data.model.CalificacionEvento

class CalificacionEventoViewModel:ViewModel() {

    private val repo= RepoCalificacionesEventos()

    fun fetchCalificacionesEventosData(idEvento:String): LiveData<MutableList<CalificacionEvento>>{
        val mutableData= MutableLiveData<MutableList<CalificacionEvento>>()
        repo.getCalificacionesEventosData(idEvento).observeForever {
            mutableData.value=it
        }
        return mutableData
    }

    fun fetchCalificacionesEventosDataXEstrellas(estrellasEventos:Int,idEvento: String): LiveData<MutableList<CalificacionEvento>>{
        val mutableData= MutableLiveData<MutableList<CalificacionEvento>>()
        repo.getCalificacionesEventosXEstrellasData(estrellasEventos,idEvento).observeForever {
            mutableData.value=it
        }
        return mutableData
    }

}