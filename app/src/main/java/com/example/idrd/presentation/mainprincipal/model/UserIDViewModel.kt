package com.example.idrd.presentation.mainprincipal.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.idrd.data.database.RepoUser
import com.example.idrd.data.model.Users

class UserIDViewModel:ViewModel() {
    private val repo = RepoUser()

    fun fetchDataIDUser(id:String): LiveData<MutableList<Users>> {
        var mutableData= MutableLiveData <MutableList<Users>>()
        repo.getUserIDData(id).observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}