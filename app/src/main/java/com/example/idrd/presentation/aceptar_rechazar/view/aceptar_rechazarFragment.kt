package com.example.idrd.presentation.aceptar_rechazar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Solicitud
import kotlinx.android.synthetic.main.fragment_aceptar_rechazar.view.*
import kotlinx.android.synthetic.main.fragment_descripcion.view.*
import java.text.SimpleDateFormat

class aceptar_rechazarFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_aceptar_rechazar, container, false)

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

        }



        return view
    }


}