package com.example.idrd.presentation.notificaciones.notificaciones_Presenter

import com.example.idrd.data.model.Notificacion
import com.example.idrd.domain.interactor.notificaciones_Interactor.Notificaciones_Interactor
import com.example.idrd.presentation.cambiar_correo.exceptions.Firebasecambiar_correoExceptions
import com.example.idrd.presentation.notificaciones.view.Notificaciones_Contract
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Notificaciones_Presenter(notificacionesInteractor: Notificaciones_Interactor) :Notificaciones_Contract.NotificacionesPresenter, CoroutineScope {

    var view: Notificaciones_Contract.NotificacionesView?=null
    var notificacionesInteractor: Notificaciones_Interactor?=null
    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job
        init {
            this.notificacionesInteractor=notificacionesInteractor
        }


    override fun attachView(view: Notificaciones_Contract.NotificacionesView) {
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

    override fun notificacion(notificacion: Notificacion, Id: String) {
        launch {
            try{
                view?.showProgressDialog()
                notificacionesInteractor?.notificacion(notificacion, Id)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e: Firebasecambiar_correoExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()

                }
            }
        }
    }

}