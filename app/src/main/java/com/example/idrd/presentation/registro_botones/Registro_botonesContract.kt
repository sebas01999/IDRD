package com.example.idrd.presentation.registro_botones

import com.example.idrd.presentation.aceptar_rechazar.Aceptar_rechazarContract

interface Registro_botonesContract  {

    interface Registro_botonesView{

        fun showError(msgError:String?)

        fun showSuccess()
        fun registroB()
    }
    interface Registro_botonesPresenter{

        fun attachView(view: Registro_botonesContract.Registro_botonesView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun registroB(aforo:Int  , id:String)

    }
}