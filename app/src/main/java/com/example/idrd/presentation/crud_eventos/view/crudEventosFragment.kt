package com.example.idrd.presentation.crud_eventos.view

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Evento
import com.example.idrd.domain.interactor.crudEventos.CrudEventosInteractorImpl
import com.example.idrd.presentation.agregarEventos.view.agregarEventoFragment

import com.example.idrd.presentation.crud_eventos.CrudEventosContract
import com.example.idrd.presentation.crud_eventos.CrudEventosPresenter.CrudEventosPresenter
import com.example.idrd.presentation.crud_eventos.model.EventosViewModelAdmin

import com.example.idrd.presentation.eventos.view.EventosAdapter
import com.example.idrd.presentation.form.model.DatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.fragment_crud_eventos.*
import kotlinx.android.synthetic.main.fragment_crud_eventos.cancelar
import kotlinx.android.synthetic.main.fragment_crud_eventos.progressBar_crud
import kotlinx.android.synthetic.main.fragment_crud_eventos.view.*
import top.defaults.colorpicker.ColorPickerPopup
import java.text.SimpleDateFormat

class crudEventosFragment : Fragment(), EventosAdapter.OnItemClickListener, CrudEventosContract.CrudEventosView {
    private lateinit var adapter: EventosAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(EventosViewModelAdmin::class.java) }
    private var evento:Evento?=null
    var filepath : Uri?=null
    var colorcard:Int=-1
    lateinit var presenter:CrudEventosPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_crud_eventos, container, false)
        presenter= CrudEventosPresenter(CrudEventosInteractorImpl())
        presenter.attachView(this)
        adapter=context?.let { EventosAdapter(it, this) }!!

        view.rv.layoutManager=LinearLayoutManager(context)
        view.rv.adapter=adapter
        observeData()

        view.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_agregar -> {
                    val dialog= agregarEventoFragment()
                    //dialog.dialog?.window?.setLayout(500, 500)
                    dialog.show(childFragmentManager, "SimpleDialog")
                    true
                }
                R.id.menu_modificar->{
                    editar()
                    true
                }
                R.id.menu_eliminar->{
                    borrar()
                    true
                }
                else -> false
            }
        }

        view.cancelar.setOnClickListener {
            evento=null
            cancelarV()
        }
        view.agregarColor.setOnClickListener {
            ColorPickerPopup.Builder(context)
                .initialColor(Color.WHITE) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("OK")
                .cancelTitle("Cancelar")
                .showIndicator(true)
                .showValue(false)
                .build()
                .show(view, object : ColorPickerPopup.ColorPickerObserver {
                    override fun onColorPicked(color: Int) {
                        colorcard= color

                    }

                    override fun onColor(color: Int, fromUser: Boolean) {}
                })
        }
        view.agregar_foto.setOnClickListener {
            addFoto()
        }
        view.btn_mas1.setOnClickListener {
            var nume=Integer.parseInt(view.num_horas.text.toString())
            nume+=1
            view.num_horas.text= nume.toString()
        }
        view.btn_menos1.setOnClickListener {
            var nume=Integer.parseInt(view.num_horas.text.toString())
            if (nume==1){
                view.btn_menos1.isSaveEnabled=false
            }else{
                nume-=1
                view.num_horas.text= nume.toString()
            }
        }
        view.datePicker.setOnClickListener {
            openDatePickerP()
        }
        view.timePicker.setOnClickListener {
            openTimePicker()
        }

        return view
    }
    private  fun openDatePickerP(){
        val picker = DatePicker{day, mont, year->onDateSelected(day, mont, year)}

        picker.show(childFragmentManager, "Material_Date_Picker")

    }
    fun onDateSelected(day:Int, month: Int, year:Int){
        val month1=month+1
        date.text="$day/$month1/$year"
    }

    private fun openTimePicker(){
        val isSystem24Hour= DateFormat.is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Selecciona la hora")
            .build()
        picker.show(childFragmentManager, "TAG")

        picker.addOnPositiveButtonClickListener {
            val formatedH = SimpleDateFormat("HH:mm")
            val h= picker.hour
            val m=picker.minute
            val hora=h.toString()+":"+m.toString()
            var salida1= formatedH.parse(hora)
            var salida=formatedH.format(salida1)
            time.text= salida.toString()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==111 && resultCode== Activity.RESULT_OK && data != null){
            filepath=data.data!!
        }
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        cancelar.visibility=View.GONE
        progressBar_crud.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        cancelar.visibility=View.VISIBLE
        progressBar_crud.visibility=View.GONE
    }

    override fun editar() {
        if (evento!=null){

            val descripcionEvento: String = etxt_descripcionEvento.editText?.text.toString().trim()
            val duracionH: String = num_horas.text.toString().trim()
            val hour: String = time.text.toString().trim()
            val date: String = date.text.toString().trim()

            if (presenter.checkEmptyDescripcion(descripcionEvento)){
                etxt_descripcionEvento.error="Ingrese la descripcion del evento"
                return
            }
            evento?.eventoDes=descripcionEvento
            evento?.duracionH=Integer.parseInt(duracionH)
            evento?.fecha=presenter.formatedDate(date, hour)
            evento?.color=colorcard


            if (filepath==null){
                presenter.editar(evento!!)
            }else{
                presenter.editarFoto(evento!!,filepath!!)
            }
            observeData()
            evento=null
        }

    }

    override fun borrar() {
        if(evento!=null){
            presenter.borrar(evento!!)
            observeData()
            evento=null
        }

    }

    override fun cancelarV() {
        mostrarEvento.visibility=View.GONE
        evento=null
    }

    override fun addFoto() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "imagen"), 111)
    }

    override fun showSuccess(msgSuccess: String?) {
        evento=null
        Toast.makeText(context,msgSuccess, Toast.LENGTH_SHORT).show()
        mostrarEvento.visibility= View.GONE
    }
    fun observeData(){
        viewModel.fetchEventosDataAdmin().observe(viewLifecycleOwner, Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility=View.GONE

            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })
    }

    override fun onItemClick(item: Evento) {
        evento=item
        val formated = SimpleDateFormat("dd'/'MM'/'yyyy")
        val dateS= formated.format(evento!!.fecha)
        val formatedH = SimpleDateFormat("HH:mm")
        val hourS= formatedH.format(evento!!.fecha)

        eventotext.setText(evento!!.eventoDes)
        date.text=dateS
        time.text=hourS
        num_horas.text=evento!!.duracionH.toString()
        mostrarEvento.visibility=View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }


}