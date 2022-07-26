package com.example.idrd.presentation.form.view

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Solicitud
import com.example.idrd.domain.interactor.FormInteractor.FormInteractorImpl
import com.example.idrd.presentation.form.FormContract
import com.example.idrd.presentation.form.model.DatePicker
import com.example.idrd.presentation.form.presenter.FormPresenter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*


class fragment_form : BottomSheetDialogFragment(), FormContract.FormView {
    lateinit var presenter: FormPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter= FormPresenter(FormInteractorImpl())
        presenter.attachView(this)

        btn_menos.setOnClickListener {
            var nume=Integer.parseInt(num_usuarios.text.toString())
            if (nume==1){
                btn_menos.isSaveEnabled=false
            }else{
                nume-=1
                num_usuarios.text= nume.toString();
            }

        }
        btn_mas.setOnClickListener {
            var nume=Integer.parseInt(num_usuarios.text.toString())

            nume+=1
            num_usuarios.text= nume.toString();

        }
        btn_menos1.setOnClickListener {
            var nume=Integer.parseInt(num_horas.text.toString())
            if (nume==1){
                btn_menos1.isSaveEnabled=false
            }else{
                nume-=1
                num_horas.text= nume.toString()
            }

        }
        btn_mas1.setOnClickListener {
            var nume=Integer.parseInt(num_horas.text.toString())

            nume+=1
            num_horas.text= nume.toString()

        }

        timePicker.setOnClickListener {
            openTimePicker()
        }
        datePicker.setOnClickListener {
            openDatePickerP()
        }
        button_enviarsolicitud.setOnClickListener {
            sendRequest()
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
        if(arguments!=null){
            val parque: Parque = arguments?.getSerializable("parque") as Parque

            var solicitud=Solicitud()
            Integer.parseInt(duracionH).also { solicitud.duracionH = it }
            Integer.parseInt(numUsers).also { solicitud.numUsers= it }
            solicitud.fecha= presenter.formatedDate(date, hour)
            solicitud.idParque=parque.id
            solicitud.nombre=parque.nombre
            solicitud.url=parque.imageUrl
            solicitud.naturaleza=nature


            presenter.sendRequest(solicitud)

        }



    }

    override fun showSuccess() {
        Toast.makeText(context,"solicitud enviada", Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}