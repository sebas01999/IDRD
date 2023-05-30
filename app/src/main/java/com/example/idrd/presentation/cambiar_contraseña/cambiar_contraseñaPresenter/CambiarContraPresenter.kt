package com.example.idrd.presentation.cambiar_contraseña.cambiar_contraseñaPresenter

import com.example.idrd.domain.interactor.cambiarcontraInteractor.CambiarcontraInteractor
import com.example.idrd.presentation.cambiar_contraseña.CambiarContraContract
import com.example.idrd.presentation.cambiar_contraseña.exceptions.FirebasecambiarContraExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CambiarContraPresenter(cambiarcontraInteractor: CambiarcontraInteractor) : CambiarContraContract.CambiarContraPresenter, CoroutineScope{


    var view: CambiarContraContract.CambiarContraView?=null
    var cambiarcontraInteractor: CambiarcontraInteractor?=null

    private val job= Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job


    init {
        this.cambiarcontraInteractor= cambiarcontraInteractor
    }

    override fun attachView(view: CambiarContraContract.CambiarContraView) {
        this.view=view
    }

    override fun dettachView() {
       view=null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view!=null
    }

    override fun cambiarcontra() {
        launch {
            try {
                view?.showProgressDialog()
                cambiarcontraInteractor?.cambiarcon()
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()

                }
            } catch (e:FirebasecambiarContraExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }

    }






}