package com.example.idrd.presentation.cambiar_correo.cambiar_correoPresenter

import com.example.idrd.domain.interactor.cambiar_correoInteractor.Cambiar_correoInteractor
import com.example.idrd.presentation.cambiar_correo.Cambiar_correoContract
import com.example.idrd.presentation.cambiar_correo.exceptions.Firebasecambiar_correoExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Cambiar_correoPresenter(cambiarCorreoInteractor: Cambiar_correoInteractor) :Cambiar_correoContract.Cambiar_correoPresenter, CoroutineScope{

    var view: Cambiar_correoContract.Cambiar_correoView?=null
    var cambiarcorreoInteractor: Cambiar_correoInteractor?=null

    private val job= Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.cambiarcorreoInteractor=cambiarCorreoInteractor
    }


    override fun attachView(view: Cambiar_correoContract.Cambiar_correoView) {
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
                cambiarcorreoInteractor?.cambiar_correo(correo)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e:Firebasecambiar_correoExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }


}