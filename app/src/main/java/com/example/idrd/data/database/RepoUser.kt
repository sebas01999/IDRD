package com.example.idrd.data.database

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RepoUser {
    private lateinit var auth: FirebaseAuth
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
    fun getUserIDData(id: String): LiveData<MutableList<Users>> {
        val mutableData = MutableLiveData<MutableList<Users>>()
        FirebaseFirestore.getInstance().collection("Users").whereEqualTo("id", id).get().addOnSuccessListener { result->
            val listData = mutableListOf<Users>()
            for (document in result){
                val user:Users = document.toObject(Users::class.java)
                listData.add(user)
            }
            mutableData.value=listData
        }
        return mutableData
    }
     fun signOut(activity: Activity) {
        // Cerrar sesión en Firebase
         auth = FirebaseAuth.getInstance()
         auth.signOut()
        // Redirigir al usuario a la pantalla de inicio de sesión
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

}