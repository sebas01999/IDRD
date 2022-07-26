package com.example.idrd.presentation.crud_eventos.CrudEventosPresenter

import android.net.Uri
import com.example.idrd.data.model.Evento
import com.example.idrd.domain.interactor.crudEventos.CrudEventoInteractor
import com.example.idrd.presentation.crud_eventos.CrudEventosContract
import com.example.idrd.presentation.crud_parques.exceptions.FirebaseCrudExceptions
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class CrudEventosPresenter(crudEventoInteractor: CrudEventoInteractor):CrudEventosContract.CrudEventosPresenter, CoroutineScope {

    var view: CrudEventosContract.CrudEventosView?=null
    var crudEventoInteractor:CrudEventoInteractor?=null

    private val job= Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.crudEventoInteractor=crudEventoInteractor
    }

    override fun attachView(view: CrudEventosContract.CrudEventosView) {
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

    override fun editar(evento: Evento) {
        launch {

            try {
                view?.showProgressDialog()
                crudEventoInteractor?.editEvento(evento)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Evento editado correctamente")
                }
            }catch (e: FirebaseCrudExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editarFoto(evento: Evento, uri: Uri) {
        launch {

            try {
                view?.showProgressDialog()
                crudEventoInteractor?.editEventoFoto(evento,uri)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Evento editado correctamente")
                }
            }catch (e: FirebaseCrudExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun borrar(evento: Evento) {
        launch {

            try {
                view?.showProgressDialog()
                crudEventoInteractor?.borarEvento(evento)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Evento borrado correctamente")
                }
            }catch (e: FirebaseCrudExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun checkEmptyDescripcion(descripcion: String): Boolean {
        return descripcion.isEmpty()
    }

    override fun formatedDate(date: String, hour: String): Date {
        val obtenida= SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date+" "+hour);
        return obtenida
    }
}