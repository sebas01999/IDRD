package com.example.idrd.presentation.agregarCalificacion.ArgregarCalificacionPresenter

import com.example.idrd.data.model.Calificacion
import com.example.idrd.domain.interactor.calificacionInteractor.CalificacionInteractor
import com.example.idrd.presentation.agregarCalificacion.AgregarCalificacionContract
import com.example.idrd.presentation.agregarCalificacion.exceptions.FirebaseCalificacionExceptiones
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class AgregarCalificacionPresenter(calificacionInteractor: CalificacionInteractor):AgregarCalificacionContract.AgregarCalificacionPresenter, CoroutineScope {
    var view:AgregarCalificacionContract.AgregarCalificacionView?=null
    var calificacionInteractor:CalificacionInteractor?=null

    private val job=Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.calificacionInteractor=calificacionInteractor
    }

    override fun attachView(view: AgregarCalificacionContract.AgregarCalificacionView) {
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

    override fun addCalificacion(calificacion: Calificacion) {
        launch {
            try {
                view?.showProgressDialog()
                calificacionInteractor?.sendCalificacion(calificacion)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e:FirebaseCalificacionExceptiones){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editCalificacion(calificacion: Calificacion) {
        launch {
            try {
                view?.showProgressDialog()
                calificacionInteractor?.EditCalificacion(calificacion)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e:FirebaseCalificacionExceptiones){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editCalificacionParque(calificacion: String, idParque: String) {
        launch {
            try {
                view?.showProgressDialog()
                calificacionInteractor?.EditCalificacionParque(calificacion,idParque)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e:FirebaseCalificacionExceptiones){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun checkEmptyComent(comentario: String): Boolean {
        return comentario.isEmpty()
    }

}