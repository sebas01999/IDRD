package com.example.idrd.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.idrd.data.model.Aforo
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDate

class RepoAforo {
    var encontrado = false
    fun getAforoDataR(idParque: String):LiveData<MutableList<Aforo>>{
        val mutableData = MutableLiveData<MutableList<Aforo>>()
        FirebaseFirestore.getInstance().collection("Aforo").whereEqualTo("idParque", idParque).addSnapshotListener { snapshot, error ->
            val listData = mutableListOf<Aforo>()
            val fechaActual = Timestamp.now().toDate()
            for (documento in snapshot!!){
                val aforo : Aforo = documento.toObject(Aforo::class.java)
                if (fechaActual.day == aforo.fecha?.day && fechaActual.month == aforo.fecha?.month && fechaActual.year == aforo.fecha?.year ){
                    listData.add(aforo)
                }
            }
            mutableData.value=listData
        }
        return mutableData
    }
    fun getAforoUser(idParque: String):LiveData<MutableList<Aforo>>{
        val mutableData = MutableLiveData<MutableList<Aforo>>()
        var idUser= FirebaseAuth.getInstance().currentUser?.uid.toString()
        FirebaseFirestore.getInstance().collection("Aforo").whereEqualTo("idParque", idParque).addSnapshotListener { snapshot, error ->
            val listData = mutableListOf<Aforo>()
            val fechaActual = Timestamp.now().toDate()
            for (documento in snapshot!!){
                val aforo : Aforo = documento.toObject(Aforo::class.java)
                if (fechaActual.day == aforo.fecha?.day && fechaActual.month == aforo.fecha?.month && fechaActual.year == aforo.fecha?.year ){
                    listData.add(aforo)
                }
            }
            mutableData.value=listData
        }
        return mutableData
    }
}