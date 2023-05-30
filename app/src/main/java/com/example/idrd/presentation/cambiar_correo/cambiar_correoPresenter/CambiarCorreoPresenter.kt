package com.example.idrd.presentation.cambiar_correo.cambiar_correoPresenter

import com.example.idrd.domain.interactor.cambiar_correoInteractor.CambiarcorreoInteractor
import com.example.idrd.presentation.cambiar_correo.CambiarCorreoContract
import com.example.idrd.presentation.cambiar_correo.exceptions.FirebasecambiarCorreoExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CambiarCorreoPresenter(cambiarCorreoInteractor: CambiarcorreoInteractor) :
    CambiarCorreoContract.CambiarCorreoPresenter, CoroutineScope{

    var view: CambiarCorreoContract.CambiarCorreoView?=null
    var cambiarcorreoInteractor: CambiarcorreoInteractor?=null

    private val job= Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.cambiarcorreoInteractor=cambiarCorreoInteractor
    }


    override fun attachView(view: CambiarCorreoContract.CambiarCorreoView) {
        this.view=view
    }

    override fun dettachView() {
        view=null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view !=null
    }

    override fun cambiarcorreo(correo: String) {
        launch {
            try{
                view?.showProgressDialog()
                cambiarcorreoInteractor?.cambiarCorreo(correo)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e:FirebasecambiarCorreoExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }


}