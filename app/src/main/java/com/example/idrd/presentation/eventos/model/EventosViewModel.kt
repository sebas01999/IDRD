package com.example.idrd.presentation.eventos.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoEventos
import com.example.idrd.data.model.Evento

class EventosViewModel: ViewModel() {
    private val repo= RepoEventos()
    fun fetchEventosData(): LiveData<MutableList<Evento>>{
        val mutableData=MutableLiveData<MutableList<Evento>>()
        repo.getEventosData().observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}