package com.example.idrd.presentation.registro_botones.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoAforo


class AforoViewModel:ViewModel() {
    private val repo=RepoAforo()
    fun fetchAforoData(idparque:String):LiveData<Int>{
        val mutableData=MutableLiveData<Int>()
        repo.getAforoData(idparque).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}