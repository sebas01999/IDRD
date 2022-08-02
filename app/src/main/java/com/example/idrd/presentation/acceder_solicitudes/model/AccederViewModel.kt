package com.example.idrd.presentation.acceder_solicitudes.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.Reposolicitud
import com.example.idrd.data.model.Solicitud

class AccederViewModel: ViewModel() {

    private val reposolcitud= Reposolicitud()
    fun fetchSolicitudUserData(idUser:String) : LiveData<MutableList<Solicitud>>{
        val mutableData= MutableLiveData<MutableList<Solicitud>>()
        reposolcitud.getSolicitudUserData(idUser).observeForever { listasol->
            mutableData.value=listasol
        }
        return mutableData
    }
    fun fetchSolicitudAdminData(idParque:String) : LiveData<MutableList<Solicitud>>{
        val mutableData= MutableLiveData<MutableList<Solicitud>>()
        reposolcitud.getSolicitudAdminData(idParque).observeForever { listasol->
            mutableData.value=listasol
        }
        return mutableData
    }
}