package com.example.idrd.presentation.agregarCalificacionEvento.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoCalificacionesEventos

import com.example.idrd.data.model.CalificacionEvento

class CalificacionEventoUserViewModel:ViewModel(){

    private val repo=RepoCalificacionesEventos()
    fun fetchCalificacionesEventosDataUser(idUser:String, idEvento:String):LiveData<MutableList<CalificacionEvento>>{
        val mutableData= MutableLiveData<MutableList<CalificacionEvento>>()
        repo.getCalificacionesEventosXUsuarioData(idUser,idEvento).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}
