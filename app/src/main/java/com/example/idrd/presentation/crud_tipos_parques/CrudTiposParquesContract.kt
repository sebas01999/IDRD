package com.example.idrd.presentation.crud_tipos_parques


import com.example.idrd.data.model.TiposParque


interface CrudTiposParquesContract {

    interface CrudTiposView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun editar()
        fun cancelar()
        fun showSuccess(msgSuccess:String?)

    }

    interface CrudTiposPresenter{
        fun attachView(view: CrudTiposView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun editar(tipoParque:TiposParque)
        fun borrar(tipoParque:TiposParque)
        fun checkEmptyTipo(tipo: String):Boolean

    }
}