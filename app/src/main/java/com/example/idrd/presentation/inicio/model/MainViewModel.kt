package com.example.idrd.presentation.inicio.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.Repo
import com.example.idrd.data.model.Parque

class MainViewModel: ViewModel(){

    private val repo = Repo()
    fun fetchParqueData(): LiveData<MutableList<Parque>> {
        val mutableData = MutableLiveData<MutableList<Parque>>()
        repo.getParqueData().observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}