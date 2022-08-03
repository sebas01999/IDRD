package com.example.idrd.presentation.calificarParque.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoCalificaciones
import com.example.idrd.data.model.Calificacion

class CalificacionesViewModel:ViewModel() {

    private val repo= RepoCalificaciones()
    fun fetchCalificacionesData(idParque:String): LiveData<MutableList<Calificacion>> {
        val mutableData= MutableLiveData<MutableList<Calificacion>>()
        repo.getCalificacionesData(idParque).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
    fun fetchCalificacionesDataXEstrellas(estrellas:Int,idParque:String ):LiveData<MutableList<Calificacion>>{
        val mutableData= MutableLiveData<MutableList<Calificacion>>()
        repo.getCalificacionesXEstrellasData(estrellas, idParque).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}