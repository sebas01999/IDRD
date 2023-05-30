package com.example.idrd.presentation.cambiar_datospersonales.cambiar_datospPresenter

import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.cambiar_datospInteractor.CambiarDatospInteractor
import com.example.idrd.presentation.cambiar_datospersonales.CambiarDatospContract
import com.example.idrd.presentation.cambiar_datospersonales.exceptions.FirebasecambiarDatospExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CambiarDatospPresenter (cambiarDatospinteractor: CambiarDatospInteractor) : CambiarDatospContract.CambiarDatospPresenter, CoroutineScope{

    var view: CambiarDatospContract.CambiarDatospView?=null
    var cambiardatospInteractor: CambiarDatospInteractor?=null

    private val job= Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.cambiardatospInteractor=cambiarDatospinteractor
    }
    override fun attachView(view: CambiarDatospContract.CambiarDatospView) {
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
                cambiardatospInteractor?.cambiarDatosp(datosp)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e:FirebasecambiarDatospExceptions){
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