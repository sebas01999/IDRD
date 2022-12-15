package com.example.idrd.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Notificacion
import com.example.idrd.data.model.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RepoNotificaciones {

    fun getNotificacionesData(idUser:String): LiveData<MutableList<Notificacion>> {
        val mutableData= MutableLiveData<MutableList<Notificacion>>()
        val ref= FirebaseDatabase.getInstance().getReference("Notificaciones").child(idUser)
        val listener =object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listData = mutableListOf<Notificacion>()
                if (snapshot.exists()){
                    for (item in snapshot.children ){
                        val notificaciones: Notificacion = item.getValue(Notificacion::class.java)!!
                        listData.add(notificaciones)
                    }
                    mutableData.value=listData
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        ref.addValueEventListener(listener)
        return mutableData
    }
}