package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Parque
import com.google.firebase.firestore.FirebaseFirestore


class Repo {

    fun getParqueData(): LiveData<MutableList<Parque>> {
        val mutableData = MutableLiveData<MutableList<Parque>>()
        FirebaseFirestore.getInstance().collection("Parques").get().addOnSuccessListener { result->
            val listData = mutableListOf<Parque>()
            for (document in result){
                val parque:Parque = document.toObject(Parque::class.java)
                parque.id=document.id
                listData.add(parque)
            }
            mutableData.value=listData
        }
        return mutableData
    }
}