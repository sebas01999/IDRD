package com.example.idrd.presentation.crud_parques.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.presentation.inicio.model.MainViewModel
import com.example.idrd.presentation.inicio.view.MainAdapter
import kotlinx.android.synthetic.main.fragment_crud_parques.*
import kotlinx.android.synthetic.main.fragment_crud_parques.view.*



class CrudParquesFragment : Fragment(), MainAdapter.OnItemClickListener {
    private lateinit var adapter: MainAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var parque:Parque?= null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_crud_parques, container, false)
        adapter= context?.let { MainAdapter(it, this) }!!
        view.rv.layoutManager=LinearLayoutManager(context)
        view.rv.adapter=adapter
        observeData()
        view.cancelar.setOnClickListener {
            mostrarParque.visibility= View.GONE
            parque=null
        }
        return view
    }

    override fun onItemClick(item: Parque) {
        parque=item
        nombre.setText(parque!!.nombre)
        ubicacion.setText(parque!!.ubicacion)
        horario.setText(parque!!.horario)
        descripcion.setText(parque!!.descripcion)
        mostrarParque.visibility= View.VISIBLE
    }

    fun observeData(){
        //shimmer_view_container.startShimmer()
        viewModel.fetchParqueData().observe(viewLifecycleOwner , Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility=View.GONE

            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })

    }
}