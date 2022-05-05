package com.example.idrd.presentation.form.presenter

import com.example.idrd.data.model.Solicitud
import com.example.idrd.domain.interactor.FormInteractor.FormInteractor
import com.example.idrd.presentation.form.FormContract
import com.example.idrd.presentation.form.exceptions.FirebaseFormExceptions
import com.example.idrd.presentation.register.RegisterContract
import com.example.idrd.presentation.register.exceptions.FirebaseRegisterException
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class FormPresenter(formInteractor: FormInteractor):FormContract.FormPresenter,
CoroutineScope{
    var view : FormContract.FormView? = null
    var formInteractor:FormInteractor?= null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job

    init {
        this.formInteractor=formInteractor
    }
    override fun attachView(view: FormContract.FormView) {
        this.view=view
    }

    override fun dettachView() {
        view = null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view !=null
    }

    override fun sendRequest(solicitud: Solicitud) {
        launch {
            try {
                view?.showProgressDialog()
                formInteractor?.sendRequest(solicitud)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.showSuccess()
                }
            }catch (e: FirebaseFormExceptions){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }
            }
        }
    }

    override fun checkEmptyNature(nature: String): Boolean {
        return nature.isEmpty()
    }

    override fun checkEmptyDate(date: String): Boolean {
      return date.contains("Seleccione el dia")
    }

    override fun checkEmptyHour(hour: String): Boolean {
        return hour.contains("Seleccione la hora")
    }

    override fun formatedDate(date: String, hour: String): Date {
        val obtenida=SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date+" "+hour);
        return obtenida
    }


}