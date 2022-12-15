package com.example.idrd.presentation.notificaciones.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Notificacion
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_notificaciones.view.*
import kotlinx.android.synthetic.main.notificacion_item_row.view.*


class NotificacionesFragment : Fragment(), NotificacionAdapter.OnItemClickListener {

    private val viewModel by lazy { ViewModelProvider(this).get(NotificacionesViewModel::class.java) }
    private lateinit var adapter: NotificacionAdapter
    val auth= FirebaseAuth.getInstance().currentUser?.uid.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater!!.inflate(R.layout.fragment_notificaciones, container, false)

        adapter= context?.let { NotificacionAdapter(it, this) }!!

        view.rvNotificaciones.layoutManager=LinearLayoutManager(context)
        view.rvNotificaciones.adapter=adapter

        observeData()

        return view
    }
    fun observeData(){

        viewModel.fetchNotificacionesData(auth).observe(viewLifecycleOwner, Observer {
            if (!it.isEmpty()){
                val numerin =it.count { noti->
                    noti.visto==false
                }
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
                Log.d("prueba", numerin.toString())
            }
        })
    }

    override fun onItemClick(item: Notificacion) {
        if (item.visto==false){
            FirebaseDatabase.getInstance().getReference("Notificaciones").child(auth).child(item.id).child("visto").setValue(true)
        }
    }


}