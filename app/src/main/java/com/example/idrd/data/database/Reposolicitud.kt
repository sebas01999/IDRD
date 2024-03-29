package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Solicitud
import com.google.firebase.firestore.FirebaseFirestore

class Reposolicitud {
    fun getSolicitudUserData(idUser:String): LiveData<MutableList<Solicitud>> {
        val mutableData = MutableLiveData<MutableList<Solicitud>>()
        FirebaseFirestore.getInstance().collection("solicitudes").whereEqualTo("idUser",idUser).get().addOnSuccessListener { result->
            val listData = mutableListOf<Solicitud>()
            for (document in result){
                val solicitud: Solicitud = document.toObject(Solicitud::class.java)
                solicitud.id=document.id
                listData.add(solicitud)
            }
            mutableData.value=listData
        }
        return mutableData
    }
    fun getSolicitudAdminData(idparque:String): LiveData<MutableList<Solicitud>> {
        val mutableData = MutableLiveData<MutableList<Solicitud>>()
        FirebaseFirestore.getInstance().collection("solicitudes").whereEqualTo("idParque",idparque).get().addOnSuccessListener { result->
            val listData = mutableListOf<Solicitud>()
            for (document in result){
                val solicitud: Solicitud = document.toObject(Solicitud::class.java)
                solicitud.id=document.id
                listData.add(solicitud)
            }
            mutableData.value=listData
        }
        return mutableData
    }
}