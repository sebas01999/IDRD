package com.example.idrd.presentation.acceder_solicitudes.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Solicitud
import com.example.idrd.presentation.inicio.model.MainViewModel
import com.example.idrd.presentation.inicio.view.MainAdapter
import kotlinx.android.synthetic.main.item_acceder_solicitudes.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.days

class AccederAdapter( private val context: Context, private var itemClickListener: OnItemClickListener):RecyclerView.Adapter<AccederAdapter.MainViewHolder>() {
    private var datalist= mutableListOf<Solicitud>()

    public interface OnItemClickListener{
        fun onItemClick(item:Solicitud)
    }
    fun setListData(data:MutableList<Solicitud>){
        datalist=data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MainViewHolder {

        val view= LayoutInflater.from(context).inflate(R.layout.item_acceder_solicitudes,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val solicitud: Solicitud = datalist[position]
        holder.bindView(solicitud)
    }

    override fun getItemCount(): Int {
        if (datalist.size>0){
            return datalist.size
        }else{
            return 0
        }
    }

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        fun bindView(solicitud: Solicitud){

            val formatter = SimpleDateFormat("EEE, dd 'de' MMMM 'del' yyyy 'a las' hh:mm a")
            val salida= formatter.format(solicitud.fecha)
            itemView.item_card.setOnClickListener{ itemClickListener.onItemClick(solicitud)}
            Glide.with(context).load(solicitud.url).into(itemView.imagen_parque)
            itemView.parque.text=solicitud.nombre
            itemView.infor_des.text="Solicitud de prestamo para el dia " + salida +
                   " con duración de " + solicitud.duracionH + " horas"
            itemView.estado.text=solicitud.estado
        }
    }

}