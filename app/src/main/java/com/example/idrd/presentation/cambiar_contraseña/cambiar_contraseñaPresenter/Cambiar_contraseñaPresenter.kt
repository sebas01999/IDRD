package com.example.idrd.presentation.cambiar_contraseña.cambiar_contraseñaPresenter

import com.example.idrd.domain.interactor.cambiarcontraInteractor.CambiarcontraInteractor
import com.example.idrd.presentation.cambiar_contraseña.Cambiar_contraContract
import com.example.idrd.presentation.cambiar_contraseña.exceptions.Firebasecambiar_contraseñaExceptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Cambiar_contraseñaPresenter(cambiarcontraInteractor: CambiarcontraInteractor) : Cambiar_contraContract.Cambiar_contraPresenter, CoroutineScope{


    var view: Cambiar_contraContract.Cambiar_contraView?=null
    var cambiarcontraInteractor: CambiarcontraInteractor?=null

    private val job= Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job


    init {
        this.cambiarcontraInteractor= cambiarcontraInteractor
    }

    override fun attachView(view: Cambiar_contraContract.Cambiar_contraView) {
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

    override fun cambiarcontraseña() {
        launch {
            try {
                view?.showProgressDialog()
                cambiarcontraInteractor?.Cambiarcon()
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()

                }
            } catch (e:Firebasecambiar_contraseñaExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }

    }






}