package com.example.idrd.presentation.agregarEventos

import android.net.Uri
import com.example.idrd.data.model.Evento
import java.util.*

interface AgregarEventoContract {
    interface AgregarEventoView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun addEvento()
        fun addFoto()
        fun cancelar()
        fun showSuccess()
    }

    interface AgregarEventoPresenter{
        fun attachView(view:AgregarEventoView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun addEvento(evento: Evento, uri: Uri)
        fun checkEmptyDescripcion(descripcion: String):Boolean
        fun checkEmptyDate(date: String):Boolean
        fun checkEmptyHour(hour: String):Boolean
        fun formatedDate(date: String, hour: String): Date
    }
}