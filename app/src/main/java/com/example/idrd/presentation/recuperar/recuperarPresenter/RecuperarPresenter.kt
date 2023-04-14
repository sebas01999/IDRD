package com.example.idrd.presentation.recuperar.recuperarPresenter

import com.example.idrd.domain.interactor.recuperarConInteractor.RecuperarConInteractor
import com.example.idrd.presentation.recuperar.RecuperarContract
import com.example.idrd.presentation.recuperar.exceptions.FirebaseRecuperarExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RecuperarPresenter(recuperarConInteractor: RecuperarConInteractor):  RecuperarContract.RecuperarPresenter, CoroutineScope{

    var view: RecuperarContract.RecuperarView?=null
    var recuperarConInteractor:RecuperarConInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.recuperarConInteractor=recuperarConInteractor
    }
    override fun attachView(view: RecuperarContract.RecuperarView) {
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

    override fun sendEmail(email: String) {
        launch {
            try {
                view?.showProgressDialog()
                recuperarConInteractor?.recuperarCon(email)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Correo enviado correctamente")
                }
            }catch (e:FirebaseRecuperarExceptions){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun checkEmptyTipo(email: String): Boolean {
        return email.isEmpty()
    }
}