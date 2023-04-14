package com.example.idrd.presentation.agregarEventos.view

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
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.base.ValidarSolicitud
import com.example.idrd.data.model.Evento
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.crudEventos.CrudEventosInteractorImpl
import com.example.idrd.presentation.agregarEventos.AgregarEventoContract
import com.example.idrd.presentation.agregarEventos.AgregarEventoPresenter.AgregarEventoPresenter
import com.example.idrd.presentation.agregarEventos.model.ViewModelParqueID
import com.example.idrd.presentation.form.model.DatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.fragment_agregar_evento.*
import kotlinx.android.synthetic.main.fragment_agregar_evento.cancelar
import kotlinx.android.synthetic.main.fragment_agregar_evento.guardar
import kotlinx.android.synthetic.main.fragment_agregar_evento.progressBar_guardar
import kotlinx.android.synthetic.main.fragment_agregar_evento.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.absoluteValue

class agregarEventoFragment : DialogFragment(), AgregarEventoContract.AgregarEventoView,
    CoroutineScope {


    lateinit var filepath : Uri
    var colorcard:Int=-1
    lateinit var presenter:AgregarEventoPresenter
    private val vieModel by lazy { ViewModelProvider(this).get(ViewModelParqueID::class.java) }
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_agregar_evento, container, false)
        presenter = AgregarEventoPresenter(CrudEventosInteractorImpl())
        presenter.attachView(this)
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
                .show(view, object : ColorPickerObserver {
                    override fun onColorPicked(color: Int) {
                        colorcard= color

                    }

                    override fun onColor(color: Int, fromUser: Boolean) {}
                })

        }

        view.agregar_foto.setOnClickListener {
            addFoto()
        }
        view.guardar.setOnClickListener {
            addEvento()
        }
        view.cancelar.setOnClickListener {
            cancelar()
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
            time.text=salida.toString()
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
        guardar.visibility=View.GONE
        cancelar.visibility=View.GONE
        progressBar_guardar.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        guardar.visibility=View.VISIBLE
        cancelar.visibility=View.VISIBLE
        progressBar_guardar.visibility=View.GONE
    }

    override fun addEvento() {

        val descripcionEvento:String=etxt_descripcionEvento.editText?.text.toString().trim()
        val duracionH:String=num_horas.text.toString().trim()
        val hour:String=time.text.toString().trim()
        val date:String=date.text.toString().trim()

        if(presenter.checkEmptyDescripcion(descripcionEvento)){
            etxt_descripcionEvento.error="Ingrese la descripcion del evento"
            return
        }
        if (presenter.checkEmptyDate(date)){
            showError("Seleccione una fecha")
            return
        }
        if (presenter.checkEmptyHour(hour)){
            showError("Seleccione una hora")
            return
        }

        var evento=Evento()
        evento.color=colorcard
        evento.eventoDes=descripcionEvento
        evento.fecha= presenter.formatedDate(date, hour)
        evento.calificacion="0.0"
        Integer.parseInt(duracionH).also { evento.duracionH= it }
        if (arguments!=null){
            val user: Users = arguments?.getSerializable("user") as Users
            vieModel.fetchParqueData(user.rol).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                evento.idParque=it.get(0).id
                evento.nombreParque=it.get(0).nombre
                launch {
                    val validarSolicitud= ValidarSolicitud(idParque = user.rol, fecha = evento.fecha, context = requireContext(), date = date, duracionH = duracionH, parque = it.get(0))
                    if(validarSolicitud.validarsolicitud() && validarSolicitud.validarevento() && validarSolicitud.validarHorario(hour)){
                        if (filepath==null){
                            presenter.addEvento(evento)
                        }else{
                            presenter.addEventoPhoto(evento, filepath)
                        }

                    }
                }
            })


        }


    }

    override fun addFoto() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "imagen"), 111)
    }

    override fun cancelar() {
        dismiss()
    }

    override fun showSuccess() {
        evento.setText("")

        Toast.makeText(context,"Evento guardado correctamente", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }
}