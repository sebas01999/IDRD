package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.TiposParque
import com.google.firebase.firestore.FirebaseFirestore

class RepoTipos {
    fun getTiposParqueData(): LiveData<MutableList<TiposParque>> {
        val mutableData= MutableLiveData<MutableList<TiposParque>>()
        FirebaseFirestore.getInstance().collection("TiposParque").get().addOnSuccessListener { result->
            val listData= mutableListOf<TiposParque>()
            for (document in result){
                val tipos: TiposParque =document.toObject(TiposParque::class.java)
                listData.add(tipos)
            }
            mutableData.value=listData
        }
        return mutableData
    }
}