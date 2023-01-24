package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Calificacion
import com.example.idrd.data.model.CalificacionEvento
import com.google.firebase.firestore.FirebaseFirestore

class RepoCalificacionesEventos {

    fun getCalificacionesEventosData(idEvento:String): LiveData<MutableList<CalificacionEvento>> {
        val mutableData= MutableLiveData<MutableList<CalificacionEvento>>()
        FirebaseFirestore.getInstance().collection("CalificacionesEventos").whereEqualTo("idEvento",idEvento).get().addOnSuccessListener { result->
            val listData= mutableListOf<CalificacionEvento>()
            for (document in result){
                val calificacion: CalificacionEvento =document.toObject(CalificacionEvento::class.java)
                listData.add(calificacion)
            }
            mutableData.value=listData
        }
        return mutableData
    }
    fun getCalificacionesEventosXEstrellasData(estrellas:Int,idEvento:String ): LiveData<MutableList<CalificacionEvento>> {
        val mutableData= MutableLiveData<MutableList<CalificacionEvento>>()
        FirebaseFirestore.getInstance().collection("CalificacionesEventos").whereEqualTo("idEvento",idEvento).whereEqualTo("estrellas",estrellas).get().addOnSuccessListener { result->
            val listData= mutableListOf<CalificacionEvento>()
            for (document in result){
                val calificacion: CalificacionEvento =document.toObject(CalificacionEvento::class.java)
                listData.add(calificacion)
            }
            mutableData.value=listData
        }
        return mutableData
    }
    fun getCalificacionesEventosXUsuarioData(userID:String,idEvento:String  ): LiveData<MutableList<CalificacionEvento>> {
        val mutableData= MutableLiveData<MutableList<CalificacionEvento>>()
        FirebaseFirestore.getInstance().collection("CalificacionesEventos").whereEqualTo("idEvento",idEvento).whereEqualTo("idUser",userID).get().addOnSuccessListener { result->
            val listData= mutableListOf<CalificacionEvento>()
            for (document in result){
                val calificacion: CalificacionEvento =document.toObject(CalificacionEvento::class.java)
                listData.add(calificacion)
            }
            mutableData.value=listData
        }
        return mutableData
    }

}