package com.example.idrd.presentation.inicio.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.TiposParque
import com.example.idrd.presentation.descripcion.view.descripcionFragment
import com.example.idrd.presentation.inicio.model.MainViewModel
import com.example.idrd.presentation.inicio.model.TiposViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.fragment_inicio.view.*
import kotlinx.coroutines.Job
import java.security.Key

class InicioFragment : Fragment(),MainAdapter.OnItemClickListener {
    private lateinit var adapter: MainAdapter
    private var dataList = mutableListOf<Parque>()
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java)}
    private val viewModelTipos by lazy { ViewModelProvider(this).get(TiposViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater!!.inflate(R.layout.fragment_inicio, container, false)
        adapter= context?.let { MainAdapter(it, this) }!!
        view.rv.layoutManager= LinearLayoutManager(context)
        view.rv.adapter =adapter
        view.searchView.clearFocus()
        view.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        view.filtro.setOnClickListener {
            if (view.scroll.isVisible){
                view.scroll.visibility=View.GONE
                if (view.noEncontrado.isVisible){
                    view.noEncontrado.visibility=View.GONE
                    view.rv.visibility=View.VISIBLE
                }
                adapter.setListData(dataList)
                adapter.notifyDataSetChanged()
            }else{
                view.scroll.visibility=View.VISIBLE
                if (view.chip_Group.isEmpty()){
                    observeDataTipos()
                }
            }

        }
        observeData()

        return view
    }

     fun filterList(text: String?) {
         var filterList = mutableListOf<Parque>()
         for (parque in dataList){
             if (text != null) {
                 if (parque.nombre.toLowerCase().contains(text.toLowerCase())){
                     filterList.add(parque)
                 }
             }
         }
         if (filterList.isEmpty()){
             rv.visibility=View.GONE
             noEncontrado.visibility=View.VISIBLE
         }else{
             noEncontrado.visibility=View.GONE
             adapter.setFilterList(filterList)
             rv.visibility=View.VISIBLE
         }

    }

    override fun onItemClick(item: Parque) {

        val bundle=Bundle()
        bundle.putSerializable("parque", item)
        val transaction=fragmentManager?.beginTransaction()

        val fragmento = descripcionFragment()
        fragmento.arguments=bundle


        transaction?.replace(R.id.container, fragmento)

        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction?.addToBackStack(null)
        transaction?.commit()

    }



    fun observeData(){
        //shimmer_view_container.startShimmer()
        viewModel.fetchParqueData().observe(viewLifecycleOwner , Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility=View.GONE
            dataList=it
            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })

    }
    fun observeDataTipos(){
        //shimmer_view_container.startShimmer()
        viewModelTipos.fetchTiposParqueData().observe(viewLifecycleOwner , Observer {
            for (item in it){
                addChip(item)
            }
            scroll.visibility=View.VISIBLE
        })

    }

    fun addChip(tipo:TiposParque){
        val chip = Chip(context)
        chip.text=tipo.tipo
        chip.chipStrokeColor=resources.getColorStateList(R.color.principal)
        chip.chipStrokeWidth= 5.0F
        chip.chipBackgroundColor=resources.getColorStateList(R.color.white)
        chip.setTextColor(resources.getColorStateList(R.color.hour_park))


        chip.setOnClickListener {
            filterListTipo(tipo.tipo)
        }
        chip_Group.addView(chip)
    }
    fun filterListTipo(text: String?) {
        var filterList = mutableListOf<Parque>()
        for (parque in dataList){
            if (text != null) {
                if (parque.tipo.equals(text)){
                    filterList.add(parque)
                }
            }
        }
        if (filterList.isEmpty()){
            rv.visibility=View.GONE
            noEncontrado.visibility=View.VISIBLE
        }else{
            noEncontrado.visibility=View.GONE
            adapter.setFilterList(filterList)
            rv.visibility=View.VISIBLE
        }

    }
}
