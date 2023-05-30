package com.example.idrd.presentation.acceder_solicitudes.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Solicitud
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.acceder_solicitudes.model.AccederViewModel
import com.example.idrd.presentation.aceptar_rechazar.view.AceptarRechazarFragment
import com.example.idrd.presentation.editar_solicitud.view.FragmentEditarSolicitud
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_acceder_solicitudes.*
import kotlinx.android.synthetic.main.fragment_acceder_solicitudes.view.*


class FragmentAccederSolicitudes : Fragment(),AccederAdapter.OnItemClickListener {
    private lateinit var adapter: AccederAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(AccederViewModel::class.java)}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View= inflater.inflate(R.layout.fragment_acceder_solicitudes, container, false)
        val user: Users = arguments?.getSerializable("user") as Users
        adapter=context?.let { AccederAdapter(it,this, user.rol) }!!
        view.rv.layoutManager=LinearLayoutManager(context)
        view.rv.adapter=adapter
        observeData()

        return view
    }

    override fun onItemClick(item: Solicitud) {
        if (arguments!=null) {
            val bundle = Bundle()
            bundle.putSerializable("solicitud", item)
            val user: Users = arguments?.getSerializable("user") as Users
            if(!user.rol.equals("USER")) {

                val transaction = fragmentManager?.beginTransaction()
                val fragmento = AceptarRechazarFragment()
                fragmento.arguments = bundle
                transaction?.replace(R.id.container, fragmento)
                transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }else{
                val dialog= FragmentEditarSolicitud()
                dialog.arguments=bundle
                dialog.show(childFragmentManager, "SimpleDialog")
            }
        }

    }

    fun observeData(){
        if (arguments!=null){
            val user: Users = arguments?.getSerializable("user") as Users
            if (user.rol.equals("USER")){
                viewModel.fetchSolicitudUserData(user.id).observe(viewLifecycleOwner , Observer {
                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.visibility=View.GONE

                    var fechaActual= Timestamp.now().toDate()
                    val lista=it.filter { x -> x.fecha!!.compareTo(fechaActual)>0}
                    if (!lista.isEmpty()){
                        adapter.setListData(lista as MutableList<Solicitud>)
                        adapter.notifyDataSetChanged()
                    }else{
                        noEncontrado.visibility=View.VISIBLE
                    }
                })
            }else{
                viewModel.fetchSolicitudAdminData(user.rol).observe(viewLifecycleOwner , Observer {
                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.visibility=View.GONE

                    var fechaActual= Timestamp.now().toDate()
                    val lista=it.filter { x -> x.fecha!!.compareTo(fechaActual)>0 && x.estado=="En espera"}
                    if (!lista.isEmpty()){
                        adapter.setListData(lista as MutableList<Solicitud>)
                        adapter.notifyDataSetChanged()
                    }else{
                        noEncontrado.visibility=View.VISIBLE
                    }


                })
            }
        }


    }

}