package com.example.idrd.presentation.acceder_solicitudes.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.idrd.R
import com.example.idrd.data.model.Solicitud
import com.example.idrd.domain.interactor.FormInteractor.FormInteractorImpl
import com.example.idrd.presentation.acceder_solicitudes.AccederSolicitudesContract
import com.example.idrd.presentation.acceder_solicitudes.acceder_solicitudesPresenter.AccederSolicitudesPresenter
import kotlinx.android.synthetic.main.item_acceder_solicitudes.view.estado
import kotlinx.android.synthetic.main.item_acceder_solicitudes.view.imagen_parque
import kotlinx.android.synthetic.main.item_acceder_solicitudes.view.infor_des
import kotlinx.android.synthetic.main.item_acceder_solicitudes.view.item_card
import kotlinx.android.synthetic.main.item_acceder_solicitudes.view.parque
import kotlinx.android.synthetic.main.item_acceder_solicitudes_user.view.*
import java.text.SimpleDateFormat

class AccederAdapter( private val context: Context, private var itemClickListener: OnItemClickListener, private val rol:String):RecyclerView.Adapter<AccederAdapter.MainViewHolder>() {
    private var datalist= mutableListOf<Solicitud>()

    public interface OnItemClickListener{
        fun onItemClick(item:Solicitud)
    }
    fun setListData(data:MutableList<Solicitud>){
        datalist=data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MainViewHolder {
        if (rol=="USER"){
            val view= LayoutInflater.from(context).inflate(R.layout.item_acceder_solicitudes_user,parent,false)
            return MainViewHolder(view)
        }else{
            val view= LayoutInflater.from(context).inflate(R.layout.item_acceder_solicitudes,parent,false)
            return MainViewHolder(view)
        }
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

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), AccederSolicitudesContract.AccederSolicitudesView{
        lateinit var presenter:AccederSolicitudesPresenter
        fun bindView(solicitud: Solicitud){
            presenter= AccederSolicitudesPresenter(FormInteractorImpl())
            presenter.attachView(this)
            if (rol=="USER") {
                val formatter = SimpleDateFormat("EEE, dd 'de' MMMM 'del' yyyy 'a las' hh:mm a")
                val salida = formatter.format(solicitud.fecha)
                Glide.with(context).load(solicitud.url).into(itemView.imagen_parque)
                itemView.parque.text = solicitud.nombre
                itemView.infor_des.text = "Solicitud de prestamo para el dia " + salida +
                        " con duración de " + solicitud.duracionH + " horas"
                itemView.estado.text = solicitud.estado
                if (solicitud.estado !="En espera"){
                    itemView.borrarSolicitud.visibility=View.GONE
                    itemView.EditarSolicitud.visibility=View.GONE
                }
                itemView.borrarSolicitud.setOnClickListener {
                    borrarSolicitud(solicitud)
                }
                itemView.EditarSolicitud.setOnClickListener {
                    itemClickListener.onItemClick(solicitud)

                }
            }else{
                val formatter = SimpleDateFormat("EEE, dd 'de' MMMM 'del' yyyy 'a las' hh:mm a")
                val salida = formatter.format(solicitud.fecha)
                itemView.item_card.setOnClickListener { itemClickListener.onItemClick(solicitud) }
                Glide.with(context).load(solicitud.url).into(itemView.imagen_parque)
                itemView.parque.text = solicitud.nombre
                itemView.infor_des.text = "Solicitud de prestamo para el dia " + salida +
                        " con duración de " + solicitud.duracionH + " horas"
                itemView.estado.text = solicitud.estado

            }
        }

        override fun showError(msgError: String?) {
            Toast.makeText(context,msgError,Toast.LENGTH_SHORT).show()
        }

        override fun showProgressDialog() {
            itemView.botones.visibility=View.GONE
            itemView.progressBar_Bsolicutud.visibility=View.VISIBLE
        }

        override fun hideProgressDialog() {
            itemView.botones.visibility=View.VISIBLE
            itemView.progressBar_Bsolicutud.visibility=View.GONE
        }

        override fun showSuccess() {
            Toast.makeText(context, "Solicitud borrada correctamente", Toast.LENGTH_SHORT).show()
        }

        override fun borrarSolicitud(solicitud: Solicitud) {
            val confirmacion = AlertDialog.Builder(context).setPositiveButton("Sí, eliminar", DialogInterface.OnClickListener { dialogInterface, i ->
                presenter.borrarSolicitud(solicitud)
                datalist.remove(solicitud)
                notifyDataSetChanged()
            }).setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            }).setTitle("Confirmar").setMessage("¿Deseas eliminar la solicitud?").create()
            confirmacion.show()
        }
    }

}