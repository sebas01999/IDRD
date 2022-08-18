package com.example.idrd.presentation.inicio.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoTipos
import com.example.idrd.data.model.TiposParque

class TiposViewModel: ViewModel() {

    private val repo =RepoTipos()
    fun fetchTiposParqueData(): LiveData<MutableList<TiposParque>>{
        val mutableData = MutableLiveData<MutableList<TiposParque>>()

        repo.getTiposParqueData().observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}