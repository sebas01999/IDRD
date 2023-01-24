package com.example.idrd.presentation.calificarEvento.view

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
import com.example.idrd.data.model.Evento
import com.example.idrd.presentation.agregarCalificacion.view.agregarCalificacionFragment
import com.example.idrd.presentation.agregarCalificacionEvento.view.AgregarCalificacionEventoFragment
import com.example.idrd.presentation.calificarEvento.model.CalificacionEventoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.calificar
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.cincoEstrella
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.cuatroEstrella
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.dosEstrella
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.rv
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.todasCali
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.tresEstrella
import kotlinx.android.synthetic.main.fragment_calificar_evento.view.unaEstrella
import kotlinx.android.synthetic.main.fragment_calificar_evento.*



class CalificarEventoFragment : BottomSheetDialogFragment() {

    private lateinit var adapter: CalificarEventoAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(CalificacionEventoViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_calificar_evento, container, false)
        var butonDrawable=view.todasCali.background
        butonDrawable= DrawableCompat.wrap(butonDrawable)
        DrawableCompat.setTint(butonDrawable, Color.parseColor("#EBF5FB"))
        view.todasCali.background=butonDrawable
        view.todasCali.setTextColor(Color.parseColor("#40BFFF"))

        adapter=context?.let { CalificarEventoAdapter(it) }!!
        view.rv.layoutManager= LinearLayoutManager(context)
        view.rv.adapter=adapter
        observeData()

        view.todasCali.setOnClickListener {
            limpiar()
            observeData()
            var butonDrawable=view.todasCali.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor("#EBF5FB"))
            view.todasCali.background=butonDrawable
            view.todasCali.setTextColor(Color.parseColor("#40BFFF"))
        }
        view.unaEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(1)
            var butonDrawable=view.unaEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor("#EBF5FB"))
            view.unaEstrella.background=butonDrawable
            view.unaEstrella.setTextColor(Color.parseColor("#40BFFF"))
        }
        view.dosEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(2)
            var butonDrawable=view.dosEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor("#EBF5FB"))
            view.dosEstrella.background=butonDrawable
            view.dosEstrella.setTextColor(Color.parseColor("#40BFFF"))
        }
        view.tresEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(3)
            var butonDrawable=view.tresEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor("#EBF5FB"))
            view.tresEstrella.background=butonDrawable
            view.tresEstrella.setTextColor(Color.parseColor("#40BFFF"))
        }
        view.cuatroEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(4)
            var butonDrawable=view.cuatroEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor("#EBF5FB"))
            view.cuatroEstrella.background=butonDrawable
            view.cuatroEstrella.setTextColor(Color.parseColor("#40BFFF"))
        }
        view.cincoEstrella.setOnClickListener {
            limpiar()
            observeDataXEstrellas(5)
            var butonDrawable=view.cincoEstrella.background
            butonDrawable=DrawableCompat.wrap(butonDrawable)
            DrawableCompat.setTint(butonDrawable,Color.parseColor("#EBF5FB"))
            view.cincoEstrella.background=butonDrawable
            view.cincoEstrella.setTextColor(Color.parseColor("#40BFFF"))
        }
        view.calificar.setOnClickListener{
            val bottomSheetFragmentCalificar= AgregarCalificacionEventoFragment()
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
        todasCali.setTextColor(Color.parseColor("#707070"))

        var buton1Drawable=unaEstrella.background
        buton1Drawable=DrawableCompat.wrap(buton1Drawable)
        DrawableCompat.setTint(buton1Drawable,Color.WHITE)
        unaEstrella.background=buton1Drawable
        unaEstrella.setTextColor(Color.parseColor("#707070"))

        var buton2Drawable=dosEstrella.background
        buton2Drawable=DrawableCompat.wrap(buton2Drawable)
        DrawableCompat.setTint(buton2Drawable,Color.WHITE)
        dosEstrella.background=buton2Drawable
        dosEstrella.setTextColor(Color.parseColor("#707070"))

        var buton3Drawable=tresEstrella.background
        buton3Drawable=DrawableCompat.wrap(buton3Drawable)
        DrawableCompat.setTint(buton3Drawable,Color.WHITE)
        tresEstrella.background=buton3Drawable
        tresEstrella.setTextColor(Color.parseColor("#707070"))

        var buton4Drawable=cuatroEstrella.background
        buton4Drawable=DrawableCompat.wrap(buton4Drawable)
        DrawableCompat.setTint(buton4Drawable,Color.WHITE)
        cuatroEstrella.background=buton4Drawable
        cuatroEstrella.setTextColor(Color.parseColor("#707070"))

        var buton5Drawable=cincoEstrella.background
        buton5Drawable=DrawableCompat.wrap(buton5Drawable)
        DrawableCompat.setTint(buton5Drawable,Color.WHITE)
        cincoEstrella.background=buton5Drawable
        cincoEstrella.setTextColor(Color.parseColor("#707070"))
    }
    fun observeData(){
        if (arguments!=null){
            val evento: Evento = arguments?.getSerializable("evento") as Evento
            viewModel.fetchCalificacionesEventosData(evento.id).observe(viewLifecycleOwner, Observer {
                shimmer_view_container.stopShimmer()
                shimmer_view_container.visibility=View.GONE
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
            })
        }

    }
    fun observeDataXEstrellas(estrellas:Int){
        if (arguments!=null) {
            val evento: Evento = arguments?.getSerializable("evento") as Evento
            viewModel.fetchCalificacionesEventosDataXEstrellas(estrellas,evento.id).observe(viewLifecycleOwner, Observer {
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