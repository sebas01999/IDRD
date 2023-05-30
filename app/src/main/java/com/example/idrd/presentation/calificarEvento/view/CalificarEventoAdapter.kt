package com.example.idrd.presentation.calificarEvento.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idrd.R
import com.example.idrd.data.model.CalificacionEvento
import kotlinx.android.synthetic.main.item_calificacion_row.view.*
import java.text.SimpleDateFormat

class CalificarEventoAdapter(private val context: Context ):RecyclerView.Adapter<CalificarEventoAdapter.CalificarEventoViewHolder>(){

    private var dataList= mutableListOf<CalificacionEvento>()
    val color1="#EBF0FF"
    val color2="#FFC833"
    fun setListData(data:MutableList<CalificacionEvento>){
        dataList=data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalificarEventoAdapter.CalificarEventoViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_calificacion_row,parent,false)
        return CalificarEventoViewHolder(view)
    }
    override fun onBindViewHolder(holder: CalificarEventoViewHolder, position: Int) {
        val calificacion: CalificacionEvento =dataList[position]
        holder.bindView(calificacion)
    }
    override fun getItemCount(): Int {
        if(dataList.size>0){
            return dataList.size
        }else{
            return 0
        }
    }
    inner class CalificarEventoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(calificacion: CalificacionEvento){
            val formatter = SimpleDateFormat("MMMM dd, yyyy")
            val salida= formatter.format(calificacion.fecha!!)
            itemView.fecha.text=salida
            if (calificacion.estrellas==1){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor(color2))
            }else  if (calificacion.estrellas==2){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor(color2))
                itemView.dosEstrella.setColorFilter(Color.parseColor(color2))
            }else if (calificacion.estrellas==3){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor(color2))
                itemView.dosEstrella.setColorFilter(Color.parseColor(color2))
                itemView.tresEstrella.setColorFilter(Color.parseColor(color2))
            }else if (calificacion.estrellas==4){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor(color2))
                itemView.dosEstrella.setColorFilter(Color.parseColor(color2))
                itemView.tresEstrella.setColorFilter(Color.parseColor(color2))
                itemView.cuatroEstrella.setColorFilter(Color.parseColor(color2))
            }else if (calificacion.estrellas==5){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor(color2))
                itemView.dosEstrella.setColorFilter(Color.parseColor(color2))
                itemView.tresEstrella.setColorFilter(Color.parseColor(color2))
                itemView.cuatroEstrella.setColorFilter(Color.parseColor(color2))
                itemView.cincoEstrella.setColorFilter(Color.parseColor(color2))
            }
            itemView.comentario.text=calificacion.comentario
        }
        fun limpiar(){
            itemView.unaEstrella.setColorFilter(Color.parseColor(color1))
            itemView.dosEstrella.setColorFilter(Color.parseColor(color1))
            itemView.tresEstrella.setColorFilter(Color.parseColor(color1))
            itemView.cuatroEstrella.setColorFilter(Color.parseColor(color1))
            itemView.cincoEstrella.setColorFilter(Color.parseColor(color1))
        }

    }

}