package com.example.idrd.presentation.registro_botones.registro_botonesPresenter

import com.example.idrd.domain.interactor.registroBotonesInteractor.RegistrobotonesInteractor
import com.example.idrd.presentation.registro_botones.Registro_botonesContract
import com.example.idrd.presentation.registro_botones.exceptions.FirebaseRegistroBotonesExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class registro_botonesPresenter (registrobotonesInteractor: RegistrobotonesInteractor): Registro_botonesContract.Registro_botonesPresenter, CoroutineScope {

    var view: Registro_botonesContract.Registro_botonesView?=null
    var registrobotonesInteractor: RegistrobotonesInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get()=Dispatchers.Main+job
    init {
        this.registrobotonesInteractor=registrobotonesInteractor
    }

    override fun attachView(view: Registro_botonesContract.Registro_botonesView) {
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

    override fun registroB(aforo: Int, id: String) {

        launch {
            try {

                registrobotonesInteractor?.Registrobotones(aforo, id)
                if (isViewAttached()){

                    view?.showSuccess()
                }
            } catch (e: FirebaseRegistroBotonesExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)

                }
            }
        }
    }


}