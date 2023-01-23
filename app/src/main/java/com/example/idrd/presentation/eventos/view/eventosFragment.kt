package com.example.idrd.presentation.eventos.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Evento
import com.example.idrd.presentation.eventos.model.EventosViewModel
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_eventos.*
import kotlinx.android.synthetic.main.fragment_eventos.view.*


class eventosFragment : Fragment(), EventosAdapter.OnItemClickListener {
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

    }

    fun observeData(){
        viewModel.fetchEventosData().observe(viewLifecycleOwner, Observer {
            var fechaActual= Timestamp.now().toDate()
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility=View.GONE
            val lista=it//.filter { x-> x.fecha!!.compareTo(fechaActual)>0 }
            adapter.setListData(lista as MutableList<Evento>)
            adapter.notifyDataSetChanged()
        })
    }

}