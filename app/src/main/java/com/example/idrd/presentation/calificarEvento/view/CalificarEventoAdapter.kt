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
            val salida= formatter.format(calificacion.fecha)
            itemView.fecha.text=salida
            if (calificacion.estrellas==1){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else  if (calificacion.estrellas==2){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else if (calificacion.estrellas==3){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else if (calificacion.estrellas==4){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.cuatroEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else if (calificacion.estrellas==5){
                limpiar()
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.cuatroEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.cincoEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }
            itemView.comentario.text=calificacion.comentario
        }
        fun limpiar(){
            itemView.unaEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
            itemView.dosEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
            itemView.tresEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
            itemView.cuatroEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
            itemView.cincoEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
        }

    }

}