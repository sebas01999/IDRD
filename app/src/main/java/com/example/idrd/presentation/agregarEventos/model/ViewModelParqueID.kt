package com.example.idrd.presentation.agregarEventos.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.Repo
import com.example.idrd.data.model.Parque

class ViewModelParqueID:ViewModel() {
    private val repo = Repo()
    fun fetchParqueData(id:String): LiveData<MutableList<Parque>> {
        val mutableData = MutableLiveData<MutableList<Parque>>()
        repo.getParqueIDData(id).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}