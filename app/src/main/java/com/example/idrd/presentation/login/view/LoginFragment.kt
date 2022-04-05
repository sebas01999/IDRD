package com.example.idrd.presentation.login.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.idrd.R
import com.example.idrd.domain.interactor.loginInteractor.SignInInteractorImpl
import com.example.idrd.presentation.login.LoginContract
import com.example.idrd.presentation.login.presenter.LoginPresenter
import com.example.idrd.presentation.mainprincipal.view.mainprincipal
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(), LoginContract.LoginView {
    lateinit var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater!!.inflate(R.layout.fragment_login, container, false)

        presenter = LoginPresenter(SignInInteractorImpl())
        presenter.attachView(this)
        view.button1.setOnClickListener {
            signIn()
        }
        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError,Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        progressBar_singIn.visibility=View.VISIBLE
        button1.visibility=View.GONE
    }

    override fun hideProgressDialog() {
        progressBar_singIn.visibility=View.GONE
        button1.visibility=View.VISIBLE
    }

    override fun signIn() {
        val email = editTextEmail.editText?.text.toString()
        val password =  editTextTextPassword.editText?.text.toString()
        if(presenter.checkEmptyFields(email,password))Toast.makeText(context,"Uno o ambos campos vacios",Toast.LENGTH_SHORT).show()
        else presenter.signInUserWithEmailAndPassword(email, password)
    }

    override fun navigateToMain() {
        val intent = Intent(context, mainprincipal::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    override fun navigeteToRecoverPassword() {

    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }


}