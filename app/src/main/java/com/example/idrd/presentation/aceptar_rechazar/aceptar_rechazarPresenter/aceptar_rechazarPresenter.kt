package com.example.idrd.presentation.aceptar_rechazar.aceptar_rechazarPresenter

import com.example.idrd.domain.interactor.aceptarrechazarInteractor.AceptarrechazarInteractor
import com.example.idrd.presentation.aceptar_rechazar.Aceptar_rechazarContract
import com.example.idrd.presentation.aceptar_rechazar.exceptions.Firebaseaceptar_rechazarExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class aceptar_rechazarPresenter(aceptarrechazarInteractor: AceptarrechazarInteractor): Aceptar_rechazarContract.Aceptar_rechazarPresenter, CoroutineScope {

    var view: Aceptar_rechazarContract.Aceptar_rechazarView?=null
    var aceptarrechazarInteractor:AceptarrechazarInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.aceptarrechazarInteractor=aceptarrechazarInteractor
    }


    override fun attachView(view: Aceptar_rechazarContract.Aceptar_rechazarView) {
        this.view=view
    }

    override fun dettachView() {
       view=null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
      return view != null
    }

    override fun editarsolicitud(estadosol: String, idsol: String) {
        launch {
            try {
                view?.showProgressDialog()
                aceptarrechazarInteractor?.Editsolicitud(estadosol, idsol)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()

                }
            } catch (e:Firebaseaceptar_rechazarExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }


}