package com.example.idrd.presentation.login.presenter
import com.example.idrd.domain.interactor.loginInteractor.SignInInteractor
import com.example.idrd.presentation.login.LoginContract
import com.example.idrd.presentation.login.exceptions.FirebaseLoginException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginPresenter(signInInteractor: SignInInteractor):LoginContract.LoginPresenter, CoroutineScope {

    var view: LoginContract.LoginView?= null
    var signInInteractor:SignInInteractor?=null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job
    init {
        this.signInInteractor=signInInteractor
    }

    override fun attachView(view: LoginContract.LoginView) {
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

    override fun signInUserWithEmailAndPassword(email: String, password: String) {

        launch {
            view?.showProgressDialog()

            try {
                signInInteractor?.signInWithEmailAndPassword(email,password)
                if (isViewAttached()){
                    view?.hideProgressDialog()
                    view?.navigateToMain()
                }
            }catch (e: FirebaseLoginException){
                if (isViewAttached()){
                    view?.showError(e.message)
                    view?.hideProgressDialog()
                }

            }

        }

    }

    override fun checkEmptyFields(email: String, password: String) :Boolean {
        return email.isEmpty() || password.isEmpty()
    }

}