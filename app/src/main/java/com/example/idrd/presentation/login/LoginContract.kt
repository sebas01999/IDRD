package com.example.idrd.presentation.login

interface LoginContract {
    interface LoginView{
        fun showError(msgError:String?)
        fun showProgressDialog()
        fun hideProgressDialog()
        fun signIn()
        fun navigateToMain()
        fun navigeteToRecoverPassword()
    }

    interface LoginPresenter{
        fun attachView(view:LoginView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun signInUserWithEmailAndPassword(email:String, password:String)
        fun checkEmptyFields(email: String, password: String):Boolean
    }
}