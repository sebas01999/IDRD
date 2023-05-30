package com.example.idrd.presentation.notificaciones.notificaciones_Presenter

import com.example.idrd.data.model.Notificacion
import com.example.idrd.domain.interactor.notificaciones_Interactor.NotificacionesInteractor
import com.example.idrd.presentation.cambiar_correo.exceptions.FirebasecambiarCorreoExceptions
import com.example.idrd.presentation.notificaciones.NotificacionesContract
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NotificacionesPresenter(notificacionesInteractor: NotificacionesInteractor) :
    NotificacionesContract.NotificacionesPresenter, CoroutineScope {

    var view: NotificacionesContract.NotificacionesView?=null
    var notificacionesInteractor: NotificacionesInteractor?=null
    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job
        init {
            this.notificacionesInteractor=notificacionesInteractor
        }


    override fun attachView(view: NotificacionesContract.NotificacionesView) {
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

    override fun notificacion(notificacion: Notificacion, id: String) {
        launch {
            try{

                notificacionesInteractor?.notificacion(notificacion, id)

            }catch (e: FirebasecambiarCorreoExceptions){

            }
        }
    }

}