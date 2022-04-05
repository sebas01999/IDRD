package com.example.idrd.presentation.register

import com.example.idrd.data.model.Users

interface RegisterContract {
    interface RegisterView{
        fun navigateToMain()
        fun signUp()
        fun showProgress()
        fun hideProgress()
        fun showError(errormsg:String?)
    }
    interface RegisterPresenter{
        fun attachView(view:RegisterView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached():Boolean
        fun checkEmptyFields(fullname:String):Boolean
        fun checkEmptyEmail(email:String):Boolean
        fun checkValidEmail(email:String):Boolean
        fun checkEmptyPasswords(pw1:String):Boolean
        fun checkEmptyPhone(phone: String):Boolean
        fun checkEmptyAdress(adress: String):Boolean
        fun checkEmptyCedula(cedula: String):Boolean

        fun signUp(user:Users, pw1: String )
    }
}