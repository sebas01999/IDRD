package com.example.idrd.presentation.inicio.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.presentation.descripcion.view.descripcionFragment
import com.example.idrd.presentation.inicio.model.MainViewModel
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.fragment_inicio.view.*
import kotlinx.coroutines.Job
import java.security.Key

class InicioFragment : Fragment(),MainAdapter.OnItemClickListener {
    private lateinit var adapter: MainAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java)}

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
        observeData()

        return view
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

            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })

    }
}
