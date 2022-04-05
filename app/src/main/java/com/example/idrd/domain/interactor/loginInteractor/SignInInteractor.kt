package com.example.idrd.domain.interactor.loginInteractor

interface SignInInteractor {
    suspend fun signInWithEmailAndPassword(email:String, password:String)
}