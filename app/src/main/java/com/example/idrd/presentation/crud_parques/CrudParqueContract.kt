package com.example.idrd.presentation.crud_parques

import android.net.Uri
import com.example.idrd.data.model.Parque

interface CrudParqueContract {
    interface CrudView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showProgressDialogV()
        fun hideProgressDialogV()
        fun verificar()
        fun editar()
        fun borrar()
        fun cancelarV()
        fun addFoto()
        fun showSuccess(msgSuccess:String?)
    }

    interface CrudPresenter{
        fun attachView(view:CrudView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun editar(parque: Parque)
        fun editarFoto(parque: Parque, uri: Uri)
        fun borrar(parque: Parque)
        fun checkEmptyNombre(nombre: String):Boolean
        fun checkEmptytipo(tipo: String):Boolean
        fun checkEmptyHour(hour: String):Boolean
        fun checkEmptyUbicacion(ubicacion: String):Boolean
        fun checkEmptyDescripcion(descripcion: String):Boolean
        fun checkEmptyCedulaAdmin(cedula: String):Boolean
    }
}