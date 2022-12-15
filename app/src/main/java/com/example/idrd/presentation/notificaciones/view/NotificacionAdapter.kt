package com.example.idrd.presentation.notificaciones.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Notificacion
import kotlinx.android.synthetic.main.fragment_calificar_parque.*
import kotlinx.android.synthetic.main.notificacion_item_row.view.*

class NotificacionAdapter(private val context: Context, private var itemClickListener: OnItemClickListener) : RecyclerView.Adapter<NotificacionAdapter.MainViewHolder>() {
    private var dataList= mutableListOf<Notificacion>()

    public interface OnItemClickListener{
        fun onItemClick(item: Notificacion)
    }
    fun setListData(data:MutableList<Notificacion>){
        dataList= data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacionAdapter.MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notificacion_item_row, parent, false)
        return MainViewHolder(view)
    }
    override fun onBindViewHolder(holder: NotificacionAdapter.MainViewHolder, position: Int) {
        val notificacion : Notificacion = dataList[position]
        holder.bindView(notificacion)
    }
    override fun getItemCount(): Int {
        if(dataList.size>0){
            return dataList.size
        }else{
            return 0
        }
    }

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(notificacion: Notificacion){
            itemView.tarjeta.setOnClickListener{ itemClickListener.onItemClick(notificacion)
            itemView.textoNoti.visibility=View.VISIBLE}
            itemView.tituloNoti.text = notificacion.Titulo
            itemView.textoNoti.text = notificacion.Texto
            itemView.fechaNoti.text = notificacion.Fecha.toString()
            if (notificacion.visto==true){
                var tarjeta=itemView.tarjeta.background
                tarjeta=DrawableCompat.wrap(tarjeta)
                DrawableCompat.setTint(tarjeta,Color.WHITE)
                itemView.tarjeta.background=tarjeta
                itemView.textoNoti.visibility=View.VISIBLE
            }else{
                var tarjeta=itemView.tarjeta.background
                tarjeta=DrawableCompat.wrap(tarjeta)
                DrawableCompat.setTint(tarjeta,Color.parseColor("#FAF6FF"))
                itemView.tarjeta.background=tarjeta
            }

        }
    }
}