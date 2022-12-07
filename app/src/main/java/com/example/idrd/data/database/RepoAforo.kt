package com.example.idrd.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RepoAforo {
    fun getAforoData(idParque:String):LiveData<Int> {
        val mutableData= MutableLiveData<Int>()
        val ref= FirebaseDatabase.getInstance().getReference("Parques").child(idParque)
        val listener =object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val aforo:Int= Integer.parseInt(snapshot.value.toString())
                mutableData.value=aforo
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        ref.addValueEventListener(listener)
        return mutableData
    }
}