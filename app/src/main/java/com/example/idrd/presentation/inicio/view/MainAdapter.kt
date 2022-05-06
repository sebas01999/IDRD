package com.example.idrd.presentation.inicio.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import kotlinx.android.synthetic.main.item_row.view.*

class MainAdapter(private val context: Context, private var itemClickListener:OnItemClickListener) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var dataList = mutableListOf<Parque>()


    public interface OnItemClickListener{
        fun onItemClick(item:Parque)
    }

    fun setListData(data:MutableList<Parque>){
        dataList= data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val parque : Parque = dataList[position]
        holder.bindView(parque)
    }

    override fun getItemCount(): Int {
        if(dataList.size>0){
            return dataList.size
        }else{
            return 0
        }
    }

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(parque: Parque){
            itemView.itemcard.setOnClickListener{ itemClickListener.onItemClick(parque)}
            Glide.with(context).load(parque.imageUrl).into(itemView.image_parque)
            itemView.nombre_parque.text = parque.nombre
            itemView.tipo_parque.text = parque.tipo
            itemView.calificacion_parque.text = parque.calificacion
            itemView.ubicacion_parque.text = parque.ubicacion
            itemView.horario_parque.text = parque.horario
        }
    }



}