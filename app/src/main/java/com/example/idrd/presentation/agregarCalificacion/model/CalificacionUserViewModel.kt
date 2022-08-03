package com.example.idrd.presentation.agregarCalificacion.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoCalificaciones
import com.example.idrd.data.model.Calificacion


class CalificacionUserViewModel:ViewModel() {
    private val repo=RepoCalificaciones()
    fun fetchCalificacionesDataUser(idAdmin:String, idParque:String):LiveData<MutableList<Calificacion>>{
        val mutableData= MutableLiveData<MutableList<Calificacion>>()
        repo.getCalificacionesUsuarioData(idAdmin, idParque).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}