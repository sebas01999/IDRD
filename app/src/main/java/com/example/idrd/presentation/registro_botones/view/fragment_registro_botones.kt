package com.example.idrd.presentation.registro_botones.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.registroBotonesInteractor.RegistrobotonesInteractorImpl
import com.example.idrd.presentation.registro_botones.Registro_botonesContract
import com.example.idrd.presentation.registro_botones.model.AforoViewModel
import com.example.idrd.presentation.registro_botones.registro_botonesPresenter.registro_botonesPresenter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_registro_botones.*
import kotlinx.android.synthetic.main.fragment_registro_botones.view.*


class fragment_registro_botones : Fragment() , Registro_botonesContract.Registro_botonesView {

    lateinit var presenter:registro_botonesPresenter
    private val viewModel by lazy { ViewModelProvider(this).get(AforoViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View= inflater!!.inflate(R.layout.fragment_registro_botones, container, false)
        observeData()
        presenter= registro_botonesPresenter((RegistrobotonesInteractorImpl()))
        presenter.attachView(this)

        view.btnentrada.setOnClickListener {
            registroB()
        }

        return view
    }
    fun observeData(){
        if(arguments!=null){
            val user: Users = arguments?.getSerializable("user") as Users
            viewModel.fetchAforoData(user.rol).observe(viewLifecycleOwner, Observer {
                numentradas.text=it.toString()
            })
        }

    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {
        Toast.makeText(context, "Ingreso registrado correctamente ", Toast.LENGTH_SHORT).show()
    }

    override fun registroB() {

        var sum= numentradas.text.toString()
        var consum= Integer.parseInt(sum)
        consum=consum+1
        if(arguments!=null){
            val user: Users = arguments?.getSerializable("user") as Users
            presenter.registroB(consum,user.rol)
        }



    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}