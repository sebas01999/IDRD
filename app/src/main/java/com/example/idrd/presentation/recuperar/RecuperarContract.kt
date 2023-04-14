package com.example.idrd.presentation.recuperar

import com.example.idrd.data.model.TiposParque
import com.example.idrd.presentation.crud_tipos_parques.CrudTiposParquesContract

interface RecuperarContract {
    interface RecuperarView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun sendEmail()
        fun showSuccess(msgSuccess:String?)
    }
    interface RecuperarPresenter{
        fun attachView(view: RecuperarView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun sendEmail(email: String)
        fun checkEmptyTipo(email:String):Boolean
    }
}