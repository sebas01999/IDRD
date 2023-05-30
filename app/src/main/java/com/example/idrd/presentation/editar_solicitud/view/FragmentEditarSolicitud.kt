package com.example.idrd.presentation.editar_solicitud.view

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.idrd.R
import com.example.idrd.base.ValidarSolicitud
import com.example.idrd.data.model.Notificacion
import com.example.idrd.data.model.Solicitud
import com.example.idrd.domain.interactor.FormInteractor.FormInteractorImpl
import com.example.idrd.presentation.form.FormContract
import com.example.idrd.presentation.form.model.DatePicker
import com.example.idrd.presentation.form.presenter.FormPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.android.synthetic.main.fragment_form.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class FragmentEditarSolicitud: DialogFragment() , FormContract.FormView, CoroutineScope {
    lateinit var presenter: FormPresenter
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

        val view: View = inflater.inflate(R.layout.fragment_form, container, false)
        presenter= FormPresenter(FormInteractorImpl())
        presenter.attachView(this)
        view.textoBtn.text="Editar solicitud"
        val solicitud: Solicitud = arguments?.getSerializable("solicitud") as Solicitud
        view.naturalezaedit.setText(solicitud.naturaleza)
        view.num_usuarios.text=solicitud.numUsers.toString()
        view.num_horas.text=solicitud.duracionH.toString()
        val fechaformato = SimpleDateFormat("dd'/'MM'/'yyyy")
        view.date.text=fechaformato.format(solicitud.fecha)
        val horaformato = SimpleDateFormat("HH:mm")
        view.time.text=horaformato.format(solicitud.fecha)

        view.btn_menos.setOnClickListener {
            var nume=Integer.parseInt(num_usuarios.text.toString())
            if (nume==1){
                view.btn_menos.isSaveEnabled=false
            }else{
                nume-=1
                view.num_usuarios.text= nume.toString();
            }

        }
        view.btn_mas.setOnClickListener {
            var nume=Integer.parseInt(num_usuarios.text.toString())

            nume+=1
            view.num_usuarios.text= nume.toString();

        }
        view.btn_menos1.setOnClickListener {
            var nume=Integer.parseInt(num_horas.text.toString())
            if (nume==1){
                view.btn_menos1.isSaveEnabled=false
            }else{
                nume-=1
                view.num_horas.text= nume.toString()
            }

        }
        view.btn_mas1.setOnClickListener {
            var nume=Integer.parseInt(num_horas.text.toString())

            nume+=1
            view.num_horas.text= nume.toString()

        }

        view.timePicker.setOnClickListener {
            openTimePicker()
        }
        view.datePicker.setOnClickListener {
            openDatePickerP()
        }
        view.button_enviarsolicitud.setOnClickListener {
            sendRequest()
        }
        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        button_enviarsolicitud.visibility=View.GONE
        progressBar_solicitud.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        button_enviarsolicitud.visibility=View.VISIBLE
        progressBar_solicitud.visibility=View.GONE
    }

    override fun sendRequest() {
        val nature:String = editTextNaturaleza.editText?.text.toString().trim()
        val numUsers:String = num_usuarios.text.toString().trim()
        val duracionH:String = num_horas.text.toString().trim()
        val hour:String = time.text.toString().trim()
        val date:String = date.text.toString().trim()

        if(presenter.checkEmptyNature(nature)){
            editTextNaturaleza.error="Ingrese la naturaleza del evento"
            return
        }
        if(presenter.checkEmptyDate(date)){
            showError("Seleccione una fecha")
            return
        }
        if(presenter.checkEmptyHour(hour)){
            showError("Ingrese hora")
            return
        }
        val solicitud: Solicitud = arguments?.getSerializable("solicitud") as Solicitud
        solicitud.naturaleza=nature
        solicitud.fecha= presenter.formatedDate(date, hour)
        Integer.parseInt(duracionH).also { solicitud.duracionH = it }
        Integer.parseInt(numUsers).also { solicitud.numUsers= it }
        launch {
            val validarSolicitud=ValidarSolicitud(idParque = solicitud.idParque, fecha = solicitud.fecha, context = requireContext(), date = date, duracionH = duracionH, parque = null)
            if(validarSolicitud.validarsolicitud() && validarSolicitud.validarevento() && validarSolicitud.validarParque(numUsers, hour)){
                presenter.editRequest(solicitud)
            }
        }

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
    private  fun openDatePickerP(){
        val picker = DatePicker{day, mont, year->onDateSelected(day, mont, year)}

        picker.show(childFragmentManager, "Material_Date_Picker")

    }
    fun onDateSelected(day:Int, month: Int, year:Int){
        val month1=month+1
        date.text="$day/$month1/$year"
    }
    override fun showSuccess() {
        Toast.makeText(context,"solicitud editada", Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }
}