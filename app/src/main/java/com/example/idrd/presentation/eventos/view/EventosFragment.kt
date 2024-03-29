package com.example.idrd.presentation.eventos.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Evento
import com.example.idrd.presentation.calificarEvento.view.CalificarEventoFragment
import com.example.idrd.presentation.eventos.model.EventosViewModel
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_eventos.*
import kotlinx.android.synthetic.main.fragment_eventos.view.*
import java.util.*


class EventosFragment : Fragment(), EventosAdapter.OnItemClickListener {
private lateinit var adapter:EventosAdapter
private val viewModel by lazy { ViewModelProvider(this).get(EventosViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater!!.inflate(R.layout.fragment_eventos, container, false)
        adapter=context?.let { EventosAdapter(it, this) }!!
        view.rv.layoutManager=LinearLayoutManager(context)
        view.rv.adapter=adapter
        observeData()

        return view
    }

    override fun onItemClick(item: Evento) {
        val bundle=Bundle()
        bundle.putSerializable("evento",item)

        val fragment=CalificarEventoFragment()
        fragment.arguments=bundle
        fragment.show(childFragmentManager, "BottomSheetDialog")
    }

    fun observeData(){
        viewModel.fetchEventosData().observe(viewLifecycleOwner, Observer {
            var fechaActual= Timestamp.now().toDate()
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility=View.GONE
            val lista= mutableListOf<Evento>()
            it.forEach { evento ->
                val fechaEvento = Date(evento.fecha!!.time + (1000 * 60 * 60 * 24))
                if (fechaEvento.compareTo(fechaActual)>0){
                    lista.add(evento)
                }
            }
            adapter.setListData(lista)
            adapter.notifyDataSetChanged()
        })
    }

}