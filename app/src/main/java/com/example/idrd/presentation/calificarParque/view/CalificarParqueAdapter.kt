package com.example.idrd.presentation.calificarParque.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idrd.R
import com.example.idrd.data.model.Calificacion
import com.example.idrd.data.model.Evento
import com.example.idrd.presentation.eventos.view.EventosAdapter
import kotlinx.android.synthetic.main.fragment_agregar_calificacion.*
import kotlinx.android.synthetic.main.item_calificacion_row.view.*
import java.text.SimpleDateFormat


class CalificarParqueAdapter(private val context:Context ):RecyclerView.Adapter<CalificarParqueAdapter.CalificarParqueViewHolder>() {
    private var dataList= mutableListOf<Calificacion>()

    fun setListData(data:MutableList<Calificacion>){
        dataList=data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalificarParqueAdapter.CalificarParqueViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_calificacion_row,parent,false)
        return CalificarParqueViewHolder(view)
    }
    override fun onBindViewHolder(holder: CalificarParqueViewHolder, position: Int) {
        val calificacion:Calificacion =dataList[position]
        holder.bindView(calificacion)
    }
    override fun getItemCount(): Int {
        if(dataList.size>0){
            return dataList.size
        }else{
            return 0
        }
    }
    inner class CalificarParqueViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(calificacion: Calificacion){
            val formatter = SimpleDateFormat("MMMM dd, yyyy")
            val salida= formatter.format(calificacion.fecha)
            itemView.fecha.text=salida
            if (calificacion.estrellas==1){
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else  if (calificacion.estrellas==2){
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else if (calificacion.estrellas==3){
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else if (calificacion.estrellas==4){
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.cuatroEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }else if (calificacion.estrellas==5){
                itemView.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.cuatroEstrella.setColorFilter(Color.parseColor("#FFC833"))
                itemView.cincoEstrella.setColorFilter(Color.parseColor("#FFC833"))
            }
            itemView.comentario.text=calificacion.comentario
        }
    }


}