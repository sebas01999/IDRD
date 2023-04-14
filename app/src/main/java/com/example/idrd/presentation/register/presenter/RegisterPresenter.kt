package com.example.idrd.presentation.register.presenter

import androidx.core.util.PatternsCompat
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.registerInteractor.RegisterInteractor
import com.example.idrd.presentation.register.RegisterContract
import com.example.idrd.presentation.register.exceptions.FirebaseRegisterException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterPresenter(registerInteractor: RegisterInteractor) : RegisterContract.RegisterPresenter,
    CoroutineScope {

    var view : RegisterContract.RegisterView? = null
    var registerInteractor:RegisterInteractor?=null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job

    init {
        this.registerInteractor=registerInteractor
    }

    override fun attachView(view: RegisterContract.RegisterView) {
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

    override fun checkEmptyFields(fullname: String): Boolean {
        return fullname.isEmpty()
    }

    override fun checkEmptyEmail(email: String): Boolean {
        return email.isEmpty()
    }

    override fun checkValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkEmptyPasswords(pw1: String): Boolean {
        return pw1.isEmpty()
    }

    override fun checkEmptyPhone(phone: String): Boolean {
        return phone.isEmpty()    }


    override fun checkEmptyAdress(adress: String): Boolean {
        return adress.isEmpty()    }

    override fun checkEmptyCedula(cedula: String): Boolean {
        return cedula.isEmpty()    }




    override fun signUp(users: Users, pw1: String) {
        launch {
            try {
                view?.showProgress()
                registerInteractor?.signUp(users, pw1)
                if (isViewAttached()){
                    view?.hideProgress()
                    view?.navigateToMain()
                }
            }catch (e: FirebaseRegisterException){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgress()
                }
            }
        }


    }


}