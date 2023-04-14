package com.example.idrd.presentation.codigo_QR.codigo_QR_Presener

import com.example.idrd.data.model.Aforo
import com.example.idrd.domain.interactor.registroEntradaInteractor.RegistroEntradaInteractor
import com.example.idrd.presentation.codigo_QR.CodigoQRContract
import com.example.idrd.presentation.codigo_QR.exceptions.CodigoQr_Exceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CodigoQR_Presenter(registroEntradaInteractor: RegistroEntradaInteractor) : CodigoQRContract.RegistroEntradaPresenter, CoroutineScope {

    var view: CodigoQRContract.RegistroEntradaView?=null
    var registroEntradaInteractor : RegistroEntradaInteractor ?=null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job
    init {
        this.registroEntradaInteractor=registroEntradaInteractor
    }
    override fun attachView(view: CodigoQRContract.RegistroEntradaView) {
        this.view=view
    }

    override fun dettachView() {
        view = null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view!=null
    }

    override fun registroEntrada(aforo: Aforo) {
        launch {
            try {
                registroEntradaInteractor?.Registrobotones(aforo)
                if (isViewAttached()){
                    view?.showSuccess()
                }
            }catch (e: CodigoQr_Exceptions){
                if (isViewAttached()){
                    view?.showError(e.message)
                }
            }
        }
    }

}