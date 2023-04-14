package com.example.idrd.presentation.crud_parques.CrudParquesPresenter

import android.net.Uri
import com.example.idrd.data.model.Parque
import com.example.idrd.domain.interactor.crudParques.CrudParqueInteractor
import com.example.idrd.presentation.crud_parques.CrudParqueContract
import com.example.idrd.presentation.crud_parques.exceptions.FirebaseCrudExceptions
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class CrudParquesPresenter(crudParqueInteractor: CrudParqueInteractor):CrudParqueContract.CrudPresenter, CoroutineScope {
    var view:CrudParqueContract.CrudView?=null
    var crudParqueInteractor:CrudParqueInteractor?=null

    private  val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.crudParqueInteractor=crudParqueInteractor
    }

    override fun attachView(view: CrudParqueContract.CrudView) {
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

    override fun editar(parque: Parque) {
        launch {

            try {
                view?.showProgressDialog()
                crudParqueInteractor?.editParque(parque)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Parque editado correctamente")
                }
            }catch (e: FirebaseCrudExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editarFoto(parque: Parque, uri: Uri) {
        launch {

            try {
                view?.showProgressDialog()
                crudParqueInteractor?.editParqueFoto(parque,uri)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Parque editado correctamente")
                }
            }catch (e: FirebaseCrudExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun borrar(parque: Parque) {
        launch {

            try {
                view?.showProgressDialog()
                crudParqueInteractor?.borarParque(parque)
                if(isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Parque borrado correctamente")
                }
            }catch (e: FirebaseCrudExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editParqueQuitarAdmin(parque: Parque) {
        launch {
            try {
                view?.showProgressDialog()
                crudParqueInteractor?.editParqueQuitarAdmin(parque)
                if (isViewAttached()){
                    view?.hideProgressDialog()

                }
            }catch (e:FirebaseCrudExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun editParqueCambiarAdmin(parque: Parque) {
        launch {
            try {
                view?.showProgressDialog()
                crudParqueInteractor?.editParqueCambiarAdmin(parque)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                }
            }catch (e:FirebaseCrudExceptions){
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
    override fun checkEmptyAforoMax(aforomax: String): Boolean {
        return aforomax.isEmpty()
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

    override fun checkEmptyDescripcion(descripcion: String): Boolean {
        return descripcion.isEmpty()
    }

    override fun checkEmptyCedulaAdmin(cedula: String): Boolean {
        return cedula.isEmpty()
    }


}