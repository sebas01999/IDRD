package com.example.idrd.presentation.descripcion.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.base.MyApp
import com.example.idrd.data.model.Parque
import com.example.idrd.presentation.calificarParque.view.calificarParqueFragment
import com.example.idrd.presentation.form.view.fragment_form
import com.example.idrd.presentation.mapa.view.MapsFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_descripcion.*
import kotlinx.android.synthetic.main.fragment_descripcion.view.*


class descripcionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_descripcion, container, false)
        val bundle=Bundle()
        val myApp = requireActivity().application as MyApp
        Log.d("prueba", "${myApp.user.nombre}")
        if(arguments!=null){
            val parque: Parque = arguments?.getSerializable("parque") as Parque
            view.nombre_parque.text=parque.nombre
            Glide.with(this).load(parque.imageUrl).into(view.image_parque)
            view.btn_calificacion.text=parque.calificacion
            view.descripcion_parque.text=parque.descripcion
            bundle.putSerializable("parque", parque)
            if (parque.idAdmin=="DEFAULT IDADMIN" || myApp.user.rol!="USER"){
                view.btn_prestamo.visibility=View.GONE
            }
        }



            val bottomSheetFragment= fragment_form()
            val bottomSheetFragmentCalificar=calificarParqueFragment()
            val mapa = MapsFragment()

            bottomSheetFragmentCalificar.arguments=bundle
            bottomSheetFragment.arguments=bundle
            mapa.arguments=bundle



            view.btn_prestamo.setOnClickListener {

                bottomSheetFragment.show(childFragmentManager, "BottomSheetDialog")
            }
            view.btn_calificacion.setOnClickListener {
                bottomSheetFragmentCalificar.show(childFragmentManager, "BottomSheetDialog")
            }

            view.btn_map.setOnClickListener {
                val transaction= fragmentManager?.beginTransaction()
                transaction?.replace(R.id.container, mapa)
                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction?.addToBackStack(null)
                transaction?.commit()

            }



        return view
    }



}