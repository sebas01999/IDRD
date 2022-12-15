package com.example.idrd.presentation.form

import com.example.idrd.data.model.Solicitud
import java.util.*

interface FormContract {
    interface FormView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun sendRequest()
        fun showSuccess()
    }

    interface FormPresenter{
        fun attachView(view:FormView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun sendRequest(solicitud: Solicitud )
        fun editRequest(solicitud: Solicitud)
        fun checkEmptyNature(nature: String):Boolean
        fun checkEmptyDate(date: String):Boolean
        fun checkEmptyHour(hour: String):Boolean
        fun formatedDate(date: String, hour: String):Date
        fun checkDate(date: Date, date2: Date?): Int
    }
}