package com.example.idrd.domain.interactor.registerInteractor

import com.example.idrd.data.model.Users

interface RegisterInteractor {
    suspend fun signUp(users: Users, password:String)
    suspend fun checkUsersCedula(cedula: String): Boolean
    suspend fun checkUsersCorreo(correo: String): Boolean
}