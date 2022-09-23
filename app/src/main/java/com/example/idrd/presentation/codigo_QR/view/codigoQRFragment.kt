package com.example.idrd.presentation.codigo_QR.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.domain.interactor.registroBotonesInteractor.RegistrobotonesInteractorImpl
import com.example.idrd.presentation.registro_botones.Registro_botonesContract
import com.example.idrd.presentation.registro_botones.model.AforoViewModel
import com.example.idrd.presentation.registro_botones.registro_botonesPresenter.registro_botonesPresenter
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_registro_botones.*


class codigoQRFragment : Fragment(), Registro_botonesContract.Registro_botonesView {
    lateinit var presenter: registro_botonesPresenter
    private val viewModel by lazy { ViewModelProvider(this).get(AforoViewModel::class.java) }
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
        presenter= registro_botonesPresenter((RegistrobotonesInteractorImpl()))
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

                Toast.makeText(context ,"El valor escaneado es: ${ result.contents}", Toast.LENGTH_SHORT).show()
                observer(result.contents)
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

    override fun registroB() {

    }
    fun observer(idParque:String){
        var aforo:Int=0
        val ref= FirebaseDatabase.getInstance().getReference("Parques")
        ref.child(idParque).get().addOnSuccessListener {
           aforo= Integer.parseInt(it.value.toString())
            aforo=aforo+1
            presenter.registroB(aforo, idParque)
        }

    }
}

