package com.example.idrd.presentation.notificaciones.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoNotificaciones
import com.example.idrd.data.model.Notificacion

class NotificacionesViewModel : ViewModel(){
    private val repo= RepoNotificaciones()

    fun fetchNotificacionesData(idUser:String): LiveData<MutableList<Notificacion>>{
        val mutableData= MutableLiveData <MutableList<Notificacion>>()
        repo.getNotificacionesData(idUser).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}