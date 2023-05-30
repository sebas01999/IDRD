package com.example.idrd.presentation.calificarParque.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.presentation.agregarCalificacion.view.AgregarCalificacionFragment
import com.example.idrd.presentation.calificarParque.model.CalificacionesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_calificar_parque.*
import kotlinx.android.synthetic.main.fragment_calificar_parque.view.*


class CalificarParqueFragment : BottomSheetDialogFragment() {
    private lateinit var adapter:CalificarParqueAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(CalificacionesViewModel::class.java) }
    val color1="#EBF5FB"
    val color2="#40BFFF"
    val color3="#707070"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_calificar_parque, container, false)

        var butonDrawable=view.todasCali.background
        butonDrawable=DrawableCompat.wrap(butonDrawable)
        DrawableCompat.setTint(butonDrawable,Color.parseColor(color1))
        view.todasCali.background=butonDrawable
        view.todasCali.setTextColor(Color.parseColor(color2))

        adapter=context?.let { CalificarParqueAdapter(it) }!!
        view.rv.layoutManager=LinearLayoutManager(context)
        view.rv.adapter=adapter
        observeData()

        view.todasCali.setOnClickListener {
            limpiar()
            observeData()
            var butonDrawable=view.todasCali.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor(color1))
            view.todasCali.background=butonDrawable
            view.todasCali.setTextColor(Color.parseColor(color2))
        }
        view.unaEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(1)
            var butonDrawable=view.unaEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor(color1))
            view.unaEstrella.background=butonDrawable
            view.unaEstrella.setTextColor(Color.parseColor(color2))
        }
        view.dosEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(2)
            var butonDrawable=view.dosEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor(color1))
            view.dosEstrella.background=butonDrawable
            view.dosEstrella.setTextColor(Color.parseColor(color2))
        }
        view.tresEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(3)
            var butonDrawable=view.tresEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor(color1))
            view.tresEstrella.background=butonDrawable
            view.tresEstrella.setTextColor(Color.parseColor(color2))
        }
        view.cuatroEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(4)
            var butonDrawable=view.cuatroEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor(color1))
            view.cuatroEstrella.background=butonDrawable
            view.cuatroEstrella.setTextColor(Color.parseColor(color2))
        }
        view.cincoEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(5)
            var butonDrawable=view.cincoEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor(color1))
            view.cincoEstrella.background=butonDrawable
            view.cincoEstrella.setTextColor(Color.parseColor(color2))
        }
        view.calificar.setOnClickListener{
            val bottomSheetFragmentCalificar=AgregarCalificacionFragment()
            bottomSheetFragmentCalificar.arguments=arguments
            bottomSheetFragmentCalificar.show(childFragmentManager, "BottomSheetDialog")
        }

        return view
    }
    fun limpiar(){
        var butonDrawable=todasCali.background
        butonDrawable=DrawableCompat.wrap(butonDrawable)
        DrawableCompat.setTint(butonDrawable,Color.WHITE)
        todasCali.background=butonDrawable
        todasCali.setTextColor(Color.parseColor(color3))

        var buton1Drawable=unaEstrella.background
        buton1Drawable=DrawableCompat.wrap(buton1Drawable)
        DrawableCompat.setTint(buton1Drawable,Color.WHITE)
        unaEstrella.background=buton1Drawable
        unaEstrella.setTextColor(Color.parseColor(color3))

        var buton2Drawable=dosEstrella.background
        buton2Drawable=DrawableCompat.wrap(buton2Drawable)
        DrawableCompat.setTint(buton2Drawable,Color.WHITE)
        dosEstrella.background=buton2Drawable
        dosEstrella.setTextColor(Color.parseColor(color3))

        var buton3Drawable=tresEstrella.background
        buton3Drawable=DrawableCompat.wrap(buton3Drawable)
        DrawableCompat.setTint(buton3Drawable,Color.WHITE)
        tresEstrella.background=buton3Drawable
        tresEstrella.setTextColor(Color.parseColor(color3))

        var buton4Drawable=cuatroEstrella.background
        buton4Drawable=DrawableCompat.wrap(buton4Drawable)
        DrawableCompat.setTint(buton4Drawable,Color.WHITE)
        cuatroEstrella.background=buton4Drawable
        cuatroEstrella.setTextColor(Color.parseColor(color3))

        var buton5Drawable=cincoEstrella.background
        buton5Drawable=DrawableCompat.wrap(buton5Drawable)
        DrawableCompat.setTint(buton5Drawable,Color.WHITE)
        cincoEstrella.background=buton5Drawable
        cincoEstrella.setTextColor(Color.parseColor(color3))
    }
    fun observeData(){
        if (arguments!=null){
            val parque: Parque = arguments?.getSerializable("parque") as Parque
            viewModel.fetchCalificacionesData(parque.id).observe(viewLifecycleOwner, Observer {
                shimmer_view_container.stopShimmer()
                shimmer_view_container.visibility=View.GONE
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
            })
        }

    }
    fun observeDataXEstrellas(estrellas:Int){
        if (arguments!=null) {
            val parque: Parque = arguments?.getSerializable("parque") as Parque
            viewModel.fetchCalificacionesDataXEstrellas(estrellas,parque.id).observe(viewLifecycleOwner, Observer {
                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.visibility = View.GONE
                    adapter.setListData(it)
                    adapter.notifyDataSetChanged()
                })
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}