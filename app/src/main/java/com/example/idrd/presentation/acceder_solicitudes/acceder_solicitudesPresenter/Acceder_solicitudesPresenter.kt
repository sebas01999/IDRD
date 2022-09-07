package com.example.idrd.presentation.acceder_solicitudes.acceder_solicitudesPresenter

import com.example.idrd.data.model.Solicitud
import com.example.idrd.domain.interactor.FormInteractor.FormInteractor
import com.example.idrd.presentation.acceder_solicitudes.Acceder_solicitudesContract
import com.example.idrd.presentation.form.exceptions.FirebaseFormExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Acceder_solicitudesPresenter(formInteractor: FormInteractor):Acceder_solicitudesContract.Acceder_solicitudesPresenter, CoroutineScope {
    var view : Acceder_solicitudesContract.Acceder_solicitudesView?=null
    var formInteractor: FormInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.formInteractor=formInteractor
    }

    override fun attachView(view: Acceder_solicitudesContract.Acceder_solicitudesView) {
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

    override fun borrarSolicitud(solicitud: Solicitud) {
       launch {
           try {
               view?.showProgressDialog()
               formInteractor?.deleteRequest(solicitud)
               if (isViewAttached()){
                   view?.hideProgressDialog()
                   view?.showSuccess()
               }
           } catch (e:FirebaseFormExceptions){
               if (isViewAttached()){
                   view?.showError(e.message)
                   view?.hideProgressDialog()
               }
           }
       }
    }

}