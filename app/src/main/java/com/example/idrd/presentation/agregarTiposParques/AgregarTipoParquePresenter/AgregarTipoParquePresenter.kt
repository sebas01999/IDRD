package com.example.idrd.presentation.agregarTiposParques.AgregarTipoParquePresenter

import com.example.idrd.data.model.TiposParque
import com.example.idrd.domain.interactor.crud_TiposParques.CrudTiposParquesInteractor
import com.example.idrd.presentation.agregarParque.exceptions.FirebaseAgregarExceptions
import com.example.idrd.presentation.agregarTiposParques.AgregarTipoParqueContract
import com.example.idrd.presentation.agregarTiposParques.exceptions.FirebaseAgregarTipoExceptions
import kotlinx.coroutines.*
import java.lang.NullPointerException
import kotlin.coroutines.CoroutineContext

class AgregarTipoParquePresenter (crudTiposParquesInteractor: CrudTiposParquesInteractor): AgregarTipoParqueContract.AgregarTipoPresenter,CoroutineScope {

    var view:AgregarTipoParqueContract.AgregarTipoView?=null
    var crudTiposParquesInteractor:CrudTiposParquesInteractor?=null
    private val job= Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job
    init {
        this.crudTiposParquesInteractor=crudTiposParquesInteractor
    }

    override fun attachView(view: AgregarTipoParqueContract.AgregarTipoView) {
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

    override fun addTipoParque(tipo_parque: TiposParque) {
        launch {
            try {
                view?.showProgressDialog()
                crudTiposParquesInteractor?.addTipoParque(tipo_parque)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e: FirebaseAgregarTipoExceptions){
                if(isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun checkEmptytipo(tipo: String): Boolean {
        return tipo.isEmpty()
    }

}