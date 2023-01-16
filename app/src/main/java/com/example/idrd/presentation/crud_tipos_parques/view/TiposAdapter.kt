package com.example.idrd.presentation.crud_tipos_parques.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.TiposParque
import com.example.idrd.presentation.inicio.view.MainAdapter
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_tipos_parque.view.*

class TiposAdapter(private val context:Context, private var itemClickListener:OnItemClickListener) : RecyclerView.Adapter<TiposAdapter.MainViewHolder>() {

    private var datalist = mutableListOf<TiposParque>()

    public interface OnItemClickListener{
        fun onItemClick(item:TiposParque)
    }
    fun setListData(data:MutableList<TiposParque>){
        datalist= data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tipos_parque, parent, false)
        return MainViewHolder(view)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val tipo_parque : TiposParque = datalist[position]
        holder.bindView(tipo_parque)
    }
    override fun getItemCount(): Int {
        if(datalist.size>0){
            return datalist.size
        }else{
            return 0
        }
    }

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(tipo_parque: TiposParque){
            itemView.item_tipos.setOnClickListener{ itemClickListener.onItemClick(tipo_parque)}
            itemView.item_tipos.text = tipo_parque.tipo
        }
    }

}