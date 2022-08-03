package com.example.idrd.presentation.agregarCalificacion

import android.net.Uri
import com.example.idrd.data.model.Calificacion
import com.example.idrd.data.model.Evento
import java.util.*

interface AgregarCalificacionContract {
    interface AgregarCalificacionView{
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

    interface AgregarCalificacionPresenter{
        fun attachView(view:AgregarCalificacionView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun addCalificacion(calificacion: Calificacion)
        fun editCalificacion(calificacion: Calificacion)
        fun checkEmptyComent(comentario: String):Boolean
    }

}