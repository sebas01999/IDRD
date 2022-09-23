package com.example.idrd.presentation.cambiar_datospersonales

import com.example.idrd.data.model.Users
import com.example.idrd.presentation.cambiar_correo.Cambiar_correoContract
import com.google.firebase.firestore.auth.User

interface Cambiar_datospContract {

    interface Cambiar_datospView {

        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showSuccess()
        fun cambiardatosp()

    }
    interface Cambiar_datospPresenter {

        fun attachView(view: Cambiar_datospView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun cambiardatosp(datosp:Users)
        fun checkcamposvacios(campo:String):Boolean
    }
}