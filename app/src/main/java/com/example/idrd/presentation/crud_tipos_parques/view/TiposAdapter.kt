package com.example.idrd.presentation.crud_tipos_parques.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idrd.R
import com.example.idrd.data.model.TiposParque
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
        val tipoParque : TiposParque = datalist[position]
        holder.bindView(tipoParque)
    }
    override fun getItemCount(): Int {
        if(datalist.size>0){
            return datalist.size
        }else{
            return 0
        }
    }

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(tipoParque: TiposParque){
            itemView.item_tipos.setOnClickListener{ itemClickListener.onItemClick(tipoParque)}
            itemView.item_tipos.text = tipoParque.tipo
        }
    }

}