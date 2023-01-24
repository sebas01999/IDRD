package com.example.idrd.presentation.agregarCalificacionEvento.AgregarCalificacionEventoPresenter

import com.example.idrd.data.model.CalificacionEvento
import com.example.idrd.domain.interactor.calificacionInteractor.CalificacionInteractor
import com.example.idrd.domain.interactor.calificacion_eventosInteractor.CalificacionEventoInteractor
import com.example.idrd.presentation.agregarCalificacionEvento.AgregarCalificacionEventoContract
import com.example.idrd.presentation.agregarCalificacionEvento.exceptions.FirebaseCalificacionEventosExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AgregarCalificacionEventoPresenter(calificacionEventoInteractor: CalificacionEventoInteractor):AgregarCalificacionEventoContract.AgregarCalificacionEventoPresenter,CoroutineScope {

    var view: AgregarCalificacionEventoContract.AgregarCalificacionEventoView? = null
    var calificacionEventoInteractor: CalificacionEventoInteractor? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.calificacionEventoInteractor = calificacionEventoInteractor
    }

    override fun attachView(view: AgregarCalificacionEventoContract.AgregarCalificacionEventoView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun addCalificacionEvento(calificacion: CalificacionEvento) {
        launch {
            try {
                view?.showProgressDialog()
                calificacionEventoInteractor?.sendCalificacionEvento(calificacion)
                if (isViewAttached()) {
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            } catch (e: FirebaseCalificacionEventosExceptions) {
                if (isViewAttached()) {
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editCalificacionEventoUser(calificacion: CalificacionEvento) {
        launch {
            try {
                view?.showProgressDialog()
                calificacionEventoInteractor?.EditCalificacionEvento(calificacion)
                if (isViewAttached()) {
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            } catch (e: FirebaseCalificacionEventosExceptions) {
                if (isViewAttached()) {
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editCalificacionEventoGeneral(calificacion: String, idEvento: String) {
        launch {
            try {
                view?.showProgressDialog()
                calificacionEventoInteractor?.EditCalificacionEventoGeneral(calificacion, idEvento)
                if (isViewAttached()) {
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            } catch (e: FirebaseCalificacionEventosExceptions) {
                if (isViewAttached()) {
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