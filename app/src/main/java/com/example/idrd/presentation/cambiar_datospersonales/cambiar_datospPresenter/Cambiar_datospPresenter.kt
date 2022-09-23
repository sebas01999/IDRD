package com.example.idrd.presentation.cambiar_datospersonales.cambiar_datospPresenter

import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.cambiar_datospInteractor.Cambiar_datospInteractor
import com.example.idrd.presentation.cambiar_datospersonales.Cambiar_datospContract
import com.example.idrd.presentation.cambiar_datospersonales.exceptions.Firebasecambiar_datospExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Cambiar_datospPresenter (cambiarDatospinteractor: Cambiar_datospInteractor) : Cambiar_datospContract.Cambiar_datospPresenter, CoroutineScope{

    var view: Cambiar_datospContract.Cambiar_datospView?=null
    var cambiardatospInteractor: Cambiar_datospInteractor?=null

    private val job= Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.cambiardatospInteractor=cambiarDatospinteractor
    }
    override fun attachView(view: Cambiar_datospContract.Cambiar_datospView) {
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

    override fun cambiardatosp(datosp: Users) {

        launch {
            try {
                view?.showProgressDialog()
                cambiardatospInteractor?.cambiar_datosp(datosp)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e:Firebasecambiar_datospExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }

            }
        }
    }

    override fun checkcamposvacios(campo: String): Boolean {
        return campo.isEmpty()
    }

}