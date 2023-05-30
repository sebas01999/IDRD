package com.example.idrd.presentation.crud_tipos_parques.crudTiposParquesPresenter

import com.example.idrd.data.model.TiposParque
import com.example.idrd.domain.interactor.crud_TiposParques.CrudTiposParquesInteractor
import com.example.idrd.presentation.crud_tipos_parques.CrudTiposParquesContract
import com.example.idrd.presentation.crud_tipos_parques.exceptions.FirebaseCrudTiposExceptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CrudTiposParquesPresenter(crudTiposParquesInteractor: CrudTiposParquesInteractor) :CrudTiposParquesContract.CrudTiposPresenter, CoroutineScope {

    var view: CrudTiposParquesContract.CrudTiposView?=null
    var crudTiposInteractor: CrudTiposParquesInteractor?=null

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job

    init {
        this.crudTiposInteractor=crudTiposParquesInteractor
    }

    override fun attachView(view: CrudTiposParquesContract.CrudTiposView) {
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

    override fun editar(tipoParque: TiposParque) {
        launch {
            try {
                view?.showProgressDialog()
                crudTiposInteractor?.editTipoParque(tipoParque)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Tipo de parque editado correctamente")
                }
            }catch (e: FirebaseCrudTiposExceptions){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun borrar(tipoParque: TiposParque) {

        launch{
            try {
                view?.showProgressDialog()
                crudTiposInteractor?.borarTipoParque(tipoParque)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess("Tipo de parque borrado correctamente")
                }
            }catch (e: FirebaseCrudTiposExceptions){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun checkEmptyTipo(tipo: String): Boolean {
        return tipo.isEmpty()
    }

}