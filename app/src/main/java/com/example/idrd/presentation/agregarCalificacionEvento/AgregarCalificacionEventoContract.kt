package com.example.idrd.presentation.agregarCalificacionEvento


import com.example.idrd.data.model.CalificacionEvento
import com.example.idrd.presentation.agregarCalificacion.AgregarCalificacionContract

interface AgregarCalificacionEventoContract {

    interface AgregarCalificacionEventoView {
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun addCalificacion()
        fun unaEstrella()
        fun dosEstrella()
        fun tresEstrella()
        fun cuatroEstrella()
        fun cincoEstrella()
        fun showSuccess()
    }

    interface AgregarCalificacionEventoPresenter{
        fun attachView(view:AgregarCalificacionEventoView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun addCalificacionEvento(calificacion: CalificacionEvento)
        fun editCalificacionEventoUser(calificacion: CalificacionEvento)
        fun editCalificacionEventoGeneral(calificacion: String, idEvento:String)
        fun checkEmptyComent(comentario: String):Boolean
    }



}