package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Calificacion
import com.example.idrd.data.model.Evento
import com.google.firebase.firestore.FirebaseFirestore

class RepoCalificaciones {
    fun getCalificacionesData(idParque:String): LiveData<MutableList<Calificacion>> {
        val mutableData= MutableLiveData<MutableList<Calificacion>>()
        FirebaseFirestore.getInstance().collection("Calificaciones").whereEqualTo("idParque",idParque).get().addOnSuccessListener { result->
            val listData= mutableListOf<Calificacion>()
            for (document in result){
                val calificacion: Calificacion =document.toObject(Calificacion::class.java)
                listData.add(calificacion)
            }
            mutableData.value=listData
        }
        return mutableData
    }
    fun getCalificacionesXEstrellasData(estrellas:Int,idParque:String ): LiveData<MutableList<Calificacion>> {
        val mutableData= MutableLiveData<MutableList<Calificacion>>()
        FirebaseFirestore.getInstance().collection("Calificaciones").whereEqualTo("idParque",idParque).whereEqualTo("estrellas",estrellas).get().addOnSuccessListener { result->
            val listData= mutableListOf<Calificacion>()
            for (document in result){
                val calificacion: Calificacion =document.toObject(Calificacion::class.java)
                listData.add(calificacion)
            }
            mutableData.value=listData
        }
        return mutableData
    }
    fun getCalificacionesUsuarioData(userID:String,idParque:String  ): LiveData<MutableList<Calificacion>> {
        val mutableData= MutableLiveData<MutableList<Calificacion>>()
        FirebaseFirestore.getInstance().collection("Calificaciones").whereEqualTo("idParque",idParque).whereEqualTo("idUser",userID).get().addOnSuccessListener { result->
            val listData= mutableListOf<Calificacion>()
            for (document in result){
                val calificacion: Calificacion =document.toObject(Calificacion::class.java)
                listData.add(calificacion)
            }
            mutableData.value=listData
        }
        return mutableData
    }

}
