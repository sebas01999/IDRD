package com.example.idrd.presentation.crud_eventos

import android.net.Uri
import com.example.idrd.data.model.Evento
import com.example.idrd.data.model.Parque
import java.util.*

interface CrudEventosContract {
    interface CrudEventosView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun editar()
        fun borrar()
        fun cancelarV()
        fun addFoto()
        fun showSuccess(msgSuccess:String?)
    }

    interface CrudEventosPresenter{
        fun attachView(view:CrudEventosView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun editar(evento: Evento)
        fun editarFoto(evento: Evento, uri: Uri)
        fun borrar(evento: Evento)
        fun checkEmptyDescripcion(descripcion: String):Boolean
        fun formatedDate(date: String, hour: String): Date
    }
}