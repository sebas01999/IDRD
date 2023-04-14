package com.example.idrd.presentation.codigo_QR.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.database.RepoAforo
import com.example.idrd.data.model.Aforo
import com.example.idrd.domain.interactor.registroEntradaInteractor.RegistroEntradaInteractorImpl
import com.example.idrd.presentation.codigo_QR.CodigoQRContract
import com.example.idrd.presentation.codigo_QR.codigo_QR_Presener.CodigoQR_Presenter
import com.example.idrd.presentation.info_mapa.model.AforoViewModel
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class codigoQRFragment : Fragment(), CodigoQRContract.RegistroEntradaView ,CoroutineScope {
    lateinit var presenter: CodigoQR_Presenter
    private val viewModel by lazy { ViewModelProvider(this).get(AforoViewModel::class.java)}
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrScanner()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View= inflater!!.inflate(R.layout.fragment_codigo_q_r, container, false)
        presenter= CodigoQR_Presenter((RegistroEntradaInteractorImpl()))
        presenter.attachView(this)
        return view
    }

    private fun qrScanner(){
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("lee el codigo de barras, para registrar tu ingreso")
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if(result != null){
            if(result.contents == null){
                Toast.makeText(context ,"Cancelado", Toast.LENGTH_SHORT).show()
            }else{

                registroEntrada(result.contents)
            }
        }else{

            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {
        Toast.makeText(context, "Ingreso registrado correctamente ", Toast.LENGTH_SHORT).show()
    }

    override fun registroEntrada(idParque: String) {
        val aforo = Aforo()
        aforo.idParque=idParque




        launch {

            val encontro=RegistroEntradaInteractorImpl().getAforoUser(idParque)

            if (encontro){
                showError("Ya registro su entrada en este parque")
                return@launch
            }else{
                Log.d("prueba","registro")
                presenter.registroEntrada(aforo)
                return@launch
            }
        }


    }

}

