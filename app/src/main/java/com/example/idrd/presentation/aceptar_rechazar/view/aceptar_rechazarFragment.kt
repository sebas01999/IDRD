package com.example.idrd.presentation.aceptar_rechazar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Solicitud
import com.example.idrd.domain.interactor.aceptarrechazarInteractor.AceptarrechazarInteractorImpl
import com.example.idrd.presentation.aceptar_rechazar.Aceptar_rechazarContract
import com.example.idrd.presentation.aceptar_rechazar.aceptar_rechazarPresenter.aceptar_rechazarPresenter
import kotlinx.android.synthetic.main.fragment_aceptar_rechazar.*
import kotlinx.android.synthetic.main.fragment_aceptar_rechazar.view.*
import kotlinx.android.synthetic.main.fragment_descripcion.view.*
import java.text.SimpleDateFormat

class aceptar_rechazarFragment : Fragment() , Aceptar_rechazarContract.Aceptar_rechazarView {

    var estado: String?=null

    lateinit var presenter: aceptar_rechazarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_aceptar_rechazar, container, false)
        presenter= aceptar_rechazarPresenter(AceptarrechazarInteractorImpl())
        presenter.attachView(this)



        if(arguments!=null){
            val solicitud: Solicitud = arguments?.getSerializable("solicitud") as Solicitud
            view.nombre.text=solicitud.nombre
            Glide.with(this).load(solicitud.url).into(view.image)
            view.descripcion.text=solicitud.naturaleza
            view.numusuarios.text=Integer.toString(solicitud.numUsers)
            val fechaformato = SimpleDateFormat("EEE, dd 'de' MMMM 'del' yyyy")
            val fecha= fechaformato.format(solicitud.fecha)
            val horaformato = SimpleDateFormat("hh:mm a")
            val hora= horaformato.format(solicitud.fecha)
            view.fecha.text=fecha
            view.hinicio.text=hora
            view.duracion.text=solicitud.duracionH.toString()+" Horas"
            view.btn_aceptar.setOnClickListener {
                estado= "Aceptada"
                editarsolicitud()
            }
            view.btn_rechazar.setOnClickListener {
                estado="Rechazada"
                editarsolicitud()
            }
        }

        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError,Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        btn_aceptar.visibility=View.GONE
        btn_rechazar.visibility=View.GONE
        progressBar_aceptar_rechazar.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {

        progressBar_aceptar_rechazar.visibility=View.GONE
        btn_aceptar.visibility=View.VISIBLE
        btn_rechazar.visibility=View.VISIBLE
    }

    override fun showSuccess() {
        Toast.makeText(context, "Respuesta enviada correctamente", Toast.LENGTH_SHORT).show()
    }

    override fun editarsolicitud() {

        if(arguments!=null){
            val solicitud: Solicitud = arguments?.getSerializable("solicitud") as Solicitud
            presenter.editarsolicitud(estado!!, solicitud.id)
        }
    }


}