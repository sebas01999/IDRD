package com.example.idrd.presentation.info_mapa.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoAforo
import com.example.idrd.data.model.Aforo


class AforoViewModel:ViewModel() {
    private val repo=RepoAforo()

    fun fetchAforoDataRealTime(idparque: String):LiveData<MutableList<Aforo>>{
        val mutableData = MutableLiveData<MutableList<Aforo>>()
        repo.getAforoDataR(idparque).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
    fun fetchAforoDataUser(idparque: String):LiveData<MutableList<Aforo>>{
        val mutableData = MutableLiveData<MutableList<Aforo>>()
        repo.getAforoUser(idparque).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}