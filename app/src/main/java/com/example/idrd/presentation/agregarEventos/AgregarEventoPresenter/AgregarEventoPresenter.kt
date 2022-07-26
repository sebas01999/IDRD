package com.example.idrd.presentation.agregarEventos.AgregarEventoPresenter

import android.net.Uri
import com.example.idrd.data.model.Evento
import com.example.idrd.domain.interactor.crudEventos.CrudEventoInteractor
import com.example.idrd.presentation.agregarEventos.AgregarEventoContract
import com.example.idrd.presentation.agregarParque.exceptions.FirebaseAgregarExceptions
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class AgregarEventoPresenter(crudEventoInteractor: CrudEventoInteractor):AgregarEventoContract.AgregarEventoPresenter, CoroutineScope{


    var view:AgregarEventoContract.AgregarEventoView?=null
    var crudEventoInteractor:CrudEventoInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.crudEventoInteractor=crudEventoInteractor
    }

    override fun attachView(view: AgregarEventoContract.AgregarEventoView) {
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

    override fun addEvento(evento: Evento, uri: Uri) {
        launch {
            try {
                view?.showProgressDialog()
                crudEventoInteractor?.addEventos(evento, uri)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e: FirebaseAgregarExceptions){
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

    override fun checkEmptyDate(date: String): Boolean {
        return date.contains("Seleccione el dia")
    }

    override fun checkEmptyHour(hour: String): Boolean {
        return hour.contains("Seleccione la hora")
    }

    override fun formatedDate(date: String, hour: String): Date {
        val obtenida= SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date+" "+hour);
        return obtenida
    }


}