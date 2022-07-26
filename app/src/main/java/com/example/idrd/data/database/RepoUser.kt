package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Users
import com.google.firebase.firestore.FirebaseFirestore

class RepoUser {

    fun getUserData(cedula: String): LiveData<MutableList<Users>> {
        val mutableData = MutableLiveData<MutableList<Users>>()
        FirebaseFirestore.getInstance().collection("Users").whereEqualTo("cedula", cedula).get().addOnSuccessListener { result->
            val listData = mutableListOf<Users>()
            for (document in result){
                val user:Users = document.toObject(Users::class.java)
                listData.add(user)
            }
            mutableData.value=listData
        }
        return mutableData
    }
}