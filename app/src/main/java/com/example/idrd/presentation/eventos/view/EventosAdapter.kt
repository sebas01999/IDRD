package com.example.idrd.presentation.eventos.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Evento
import kotlinx.android.synthetic.main.item_eventos_row.view.*
import java.text.SimpleDateFormat

class EventosAdapter(private val context: Context, private var itemClickListener:OnItemClickListener) : RecyclerView.Adapter<EventosAdapter.EventosViewHolder>(){
    private var dataList= mutableListOf<Evento>()
    public interface OnItemClickListener{
        fun onItemClick(item:Evento)
    }
    fun setListData(data:MutableList<Evento>){
        dataList=data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_eventos_row,parent,false)
        return EventosViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventosViewHolder, position: Int) {
        val evento:Evento=dataList[position]
        holder.bindView(evento)
    }

    override fun getItemCount(): Int {
        if(dataList.size>0){
            return dataList.size
        }else{
            return 0
        }
    }
    inner class EventosViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindView(evento: Evento){
            val formatter = SimpleDateFormat("EEE, dd 'de' MMMM 'del' yyyy 'a las' hh:mm a")
            val salida= formatter.format(evento.fecha)
            itemView.item_EventoCard.setOnClickListener { itemClickListener.onItemClick(evento) }
            itemView.item_EventoCard.setCardBackgroundColor(evento.color)
            itemView.nombre_parque.text=evento.nombreParque
            itemView.descripcion_evento.text="El proximo "+salida+evento.eventoDes+" con una duracion de "+evento.duracionH+" horas"
            Glide.with(context).load(evento.imageUrl).into(itemView.image_evento)
        }
    }

}