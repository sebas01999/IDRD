package com.example.idrd.presentation.register.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.idrd.R
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.registerInteractor.RegisterInteractorImpl
import com.example.idrd.presentation.mainprincipal.view.mainprincipal
import com.example.idrd.presentation.register.RegisterContract
import com.example.idrd.presentation.register.presenter.RegisterPresenter
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment(),RegisterContract.RegisterView {
    lateinit var presenter:RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_register, container, false)
        presenter= RegisterPresenter(RegisterInteractorImpl())
        presenter.attachView(this)
        view.btn_signup.setOnClickListener {
            signUp()
        }
        return view
    }

    override fun navigateToMain() {
        val intent = Intent(context, mainprincipal::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun signUp() {
        val fullname:String = etxt_fullname.editText?.text.toString().trim()
        val email:String = etxt_email_registro.editText?.text.toString().trim()
        val cedula:String = etxt_cedula.editText?.text.toString()
        val phone:String = etxt_phone.editText?.text.toString()
        val adress:String = etxt_adress.editText?.text.toString().trim()
        val pw1:String = etxt_contraseña.editText?.text.toString().trim()

        if (presenter.checkEmptyFields(fullname)){
            etxt_fullname.error="El nombre esta vacio"
            return
        }
        if(presenter.checkEmptyEmail(email)){
            etxt_email_registro.error="El Email es vacio"
            return
        }

        if (presenter.checkEmptyPasswords(pw1)){
            etxt_contraseña.error="contraseña vacia"
            return
        }

        if (presenter.checkEmptyCedula(cedula)){
            etxt_cedula.error="contraseña vacia"
            return
        }
        if (presenter.checkEmptyPhone(phone)){
            etxt_phone.error="contraseña vacia"
            return
        }
        if (presenter.checkEmptyCedula(adress)){
            etxt_adress.error="contraseña vacia"
            return
        }
        var users=Users()
        users.nombre=fullname
        users.cedula=cedula
        users.correo=email
        users.direction=adress
        users.telefono=phone



        presenter.signUp(users, pw1)
    }

    override fun showProgress() {
        progress_signup.visibility= View.VISIBLE
        btn_signup.visibility= View.GONE
    }

    override fun hideProgress() {
        progress_signup.visibility= View.GONE
        btn_signup.visibility= View.VISIBLE
    }

    override fun showError(errormsg: String?) {
        Toast.makeText(context,errormsg, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}