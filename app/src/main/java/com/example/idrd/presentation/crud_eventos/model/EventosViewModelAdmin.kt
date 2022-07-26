package com.example.idrd.presentation.crud_eventos.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoEventos
import com.example.idrd.data.model.Evento

class EventosViewModelAdmin: ViewModel() {
    private val repo= RepoEventos()
    fun fetchEventosDataAdmin(): LiveData<MutableList<Evento>> {
        val mutableData= MutableLiveData<MutableList<Evento>>()
        repo.getEventosDataAdmin().observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}