package com.example.idrd.presentation.aceptar_rechazar.aceptar_rechazarPresenter

import com.example.idrd.domain.interactor.aceptarrechazarInteractor.AceptarrechazarInteractor
import com.example.idrd.presentation.aceptar_rechazar.AceptarRechazarContract
import com.example.idrd.presentation.aceptar_rechazar.exceptions.FirebaseaceptarRechazarExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AceptarRechazarPresenter(aceptarrechazarInteractor: AceptarrechazarInteractor): AceptarRechazarContract.AceptarRechazarPresenter, CoroutineScope {

    var view: AceptarRechazarContract.AceptarRechazarView?=null
    var aceptarrechazarInteractor:AceptarrechazarInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.aceptarrechazarInteractor=aceptarrechazarInteractor
    }


    override fun attachView(view: AceptarRechazarContract.AceptarRechazarView) {
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
                aceptarrechazarInteractor?.editsolicitud(estadosol, idsol)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()

                }
            } catch (e:FirebaseaceptarRechazarExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }


}