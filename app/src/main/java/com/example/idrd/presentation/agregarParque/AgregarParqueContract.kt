package com.example.idrd.presentation.agregarParque

import android.net.Uri
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Users
import java.util.*

interface AgregarParqueContract {
    interface AgregarView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun showProgressDialogV()
        fun hideProgressDialogV()
        fun verificar()
        fun addParque()
        fun addFoto()
        fun cancelar()
        fun showSuccess()
    }

    interface AgregarPresenter{
        fun attachView(view:AgregarView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun addParque(parque: Parque, uri:Uri)
        fun checkEmptyNombre(nombre: String):Boolean
        fun checkEmptytipo(tipo: String):Boolean
        fun checkHour(hourA: String, hourC: String):Boolean
        fun checkEmptyUbicacion(ubicacion: String):Boolean
        fun checkEmptyAforoMax(aforomax: String):Boolean
        fun checkEmptyDescripcion(descripcion: String):Boolean
        fun checkEmptyCedulaAdmin(cedula: String):Boolean

    }
}