package com.example.idrd.presentation.agregarParque.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoUser
import com.example.idrd.data.model.Users

class UserViewModel:ViewModel() {

    private val repo = RepoUser()

    fun fetchDataUser(cedula:String):LiveData<MutableList<Users>>{
        var mutableData= MutableLiveData <MutableList<Users>>()
        repo.getUserData(cedula).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
    fun fetchDataAllUser():LiveData<MutableList<Users>>{
        var mutableData= MutableLiveData <MutableList<Users>>()
        repo.getAllUserData().observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}