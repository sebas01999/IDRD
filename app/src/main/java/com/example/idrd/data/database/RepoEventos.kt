package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Evento
import com.google.firebase.firestore.FirebaseFirestore

class RepoEventos {

    fun getEventosData():LiveData<MutableList<Evento>>{
        val mutableData=MutableLiveData<MutableList<Evento>>()
        FirebaseFirestore.getInstance().collection("Eventos").get().addOnSuccessListener { result->
            val listData= mutableListOf<Evento>()
            for (document in result){
                val evento:Evento=document.toObject(Evento::class.java)
                listData.add(evento)
            }
            mutableData.value=listData
        }
        return mutableData
    }
    fun getEventosDataAdmin(idAdmin:String):LiveData<MutableList<Evento>>{
        val mutableData=MutableLiveData<MutableList<Evento>>()
        FirebaseFirestore.getInstance().collection("Eventos").whereEqualTo("idParque",idAdmin).get().addOnSuccessListener { result->
            val listData= mutableListOf<Evento>()
            for (document in result){
                val evento:Evento=document.toObject(Evento::class.java)
                listData.add(evento)
            }
            mutableData.value=listData
        }
        return mutableData
    }
}