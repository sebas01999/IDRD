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
import com.example.idrd.presentation.mainprincipal.view.Mainprincipal
import com.example.idrd.presentation.register.RegisterContract
import com.example.idrd.presentation.register.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterFragment : Fragment(),RegisterContract.RegisterView,
    CoroutineScope {
    lateinit var presenter:RegisterPresenter
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job
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
        val intent = Intent(context, Mainprincipal::class.java)
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
        launch {
            showProgress()
            if (RegisterInteractorImpl().checkUsersCedula(cedula)){
                etxt_cedula.error="Esta cedula ya existe"
                return@launch
            }
            if (RegisterInteractorImpl().checkUsersCorreo(email)){
                etxt_email_registro.error="Este correo ya existe"
                return@launch
            }
            if (presenter.checkEmptyFields(fullname)){
                etxt_fullname.error="El nombre esta vacio"
                return@launch
            }
            if(presenter.checkEmptyEmail(email)){
                etxt_email_registro.error="El Email es vacio"
                return@launch
            }

            if (presenter.checkEmptyPasswords(pw1)){
                etxt_contraseña.error="contraseña vacia"
                return@launch
            }

            if (presenter.checkEmptyCedula(cedula)){
                etxt_cedula.error="contraseña vacia"
                return@launch
            }


            if (presenter.checkEmptyPhone(phone)){
                etxt_phone.error="telefono vacio"
                return@launch
            }
            if (presenter.checkEmptyCedula(adress)){
                etxt_adress.error="direccion vacia"
                return@launch
            }

            var users=Users()
            users.nombre=fullname
            users.cedula=cedula
            users.correo=email
            users.direction=adress
            users.telefono=phone
            presenter.signUp(users, pw1)
        }
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