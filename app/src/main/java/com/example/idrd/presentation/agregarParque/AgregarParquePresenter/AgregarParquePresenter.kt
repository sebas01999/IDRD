package com.example.idrd.presentation.agregarParque.AgregarParquePresenter

import android.net.Uri
import com.example.idrd.data.model.Parque
import com.example.idrd.domain.interactor.crudParques.CrudParqueInteractor
import com.example.idrd.presentation.agregarParque.AgregarParqueContract
import com.example.idrd.presentation.agregarParque.exceptions.FirebaseAgregarExceptions
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class AgregarParquePresenter(crudParqueInteractor: CrudParqueInteractor):AgregarParqueContract.AgregarPresenter, CoroutineScope {

    var view:AgregarParqueContract.AgregarView?=null
    var crudParqueInteractor:CrudParqueInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.crudParqueInteractor=crudParqueInteractor
    }
    override fun attachView(view: AgregarParqueContract.AgregarView) {
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

    override fun addParque(parque: Parque, uri: Uri) {
        launch {
            try {
                view?.showProgressDialog()
                crudParqueInteractor?.addParque(parque, uri)
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

    override fun checkEmptyNombre(nombre: String): Boolean {
        return nombre.isEmpty()
    }

    override fun checkEmptytipo(tipo: String): Boolean {
        return tipo.isEmpty()
    }

    override fun checkHour(hourA: String, hourC: String): Boolean {
        val formatedH = SimpleDateFormat("HH:mm")
        val horaApertura= formatedH.parse(hourA)
        val horaCierre= formatedH.parse(hourC)

        return horaApertura.after(horaCierre)
    }

    override fun checkEmptyUbicacion(ubicacion: String): Boolean {
        return ubicacion.isEmpty()
    }

    override fun checkEmptyAforoMax(aforomax: String): Boolean {
        return aforomax.isEmpty()
    }

    override fun checkEmptyDescripcion(descripcion: String): Boolean {
        return descripcion.isEmpty()
    }

    override fun checkEmptyCedulaAdmin(cedula: String): Boolean {
        return cedula.isEmpty()
    }


}