package com.example.idrd.presentation.codigo_QR

import com.example.idrd.data.model.Aforo

interface CodigoQRContract {
    interface RegistroEntradaView{

        fun showError(msgError:String?)
        fun showSuccess()
        fun registroEntrada(idParque:String)
    }
    interface RegistroEntradaPresenter{

        fun attachView(view: RegistroEntradaView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun registroEntrada(aforo: Aforo)

    }
}