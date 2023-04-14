package com.example.idrd.presentation.agregarParque.view

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.crudParques.CrudParqueInteractorImpl
import com.example.idrd.presentation.agregarParque.AgregarParqueContract
import com.example.idrd.presentation.agregarParque.AgregarParquePresenter.AgregarParquePresenter
import com.example.idrd.presentation.agregarParque.model.UserViewModel
import com.example.idrd.presentation.inicio.model.TiposViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.fragment_agregar_parque.*
import kotlinx.android.synthetic.main.fragment_agregar_parque.view.*
import java.text.SimpleDateFormat


class agregarParqueFragment : DialogFragment(), AgregarParqueContract.AgregarView {
    lateinit var filepath : Uri
    lateinit var presenter: AgregarParquePresenter
    var encontrado=false
    var verificado:Users?=null
    var tipoP: String?=null
    var ubicacionp:GeoPoint?= GeoPoint(0.0, 0.0)
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private var dataListTipos = mutableListOf<String>()
    private val viewModel by lazy { ViewModelProvider(this).get(UserViewModel::class.java) }
    private val viewModelTipos by lazy { ViewModelProvider(this).get(TiposViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_agregar_parque, container, false)
        presenter= AgregarParquePresenter(CrudParqueInteractorImpl())
        presenter.attachView(this)

        viewModelTipos.fetchTiposParqueData().observe(viewLifecycleOwner, Observer {lista->
            dataListTipos.add("Seleccione un tipo")
            for (item in lista){
                dataListTipos.add(item.tipo)
            }
            val adapter= context?.let {
                ArrayAdapter(
                    it,
                    R.layout.item_tipos_parque,
                    dataListTipos
                )
            }
            view.spiner.adapter=adapter

        })

        Places.initialize(context, getString(R.string.placeKey))

        view.spiner.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tipoP= dataListTipos.get(p2)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        view.spiner.setSelection(3)
        view.etxt_nombre.addOnEditTextAttachedListener {
            if (encontrado==false){
                prueba()
            }
        }
        view.agregar_foto.setOnClickListener {
            addFoto()
        }
        view.guardar.setOnClickListener {
            addParque()
        }
        view.cancelar.setOnClickListener {
            cancelar()
        }
        view.verificar.setOnClickListener {
            verificar()
        }
        view.borrar.setOnClickListener {
            nombre.setText("")
            ubicacion.setText("")
            //horario.setText("")
            descripcion.setText("")
            limpiarHorarios()
            borrar.visibility=View.GONE
            encontrado=false
        }
        view.DomingoCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (view.DomingoCheck.isChecked){
                view.DomingotimePicker.isClickable=false
                view.Domingotime.text="Apertura"
                view.DomingotimePickerCierre.isClickable=false
                view.DomingotimeCierre.text="Cierre"
            }else{
                view.DomingotimePicker.isClickable=true
                view.DomingotimePickerCierre.isClickable=true
            }
        }
        view.DomingotimePicker.setOnClickListener {
            openTimePicker(view.Domingotime)
        }
        view.DomingotimePickerCierre.setOnClickListener {
            openTimePicker(view.DomingotimeCierre)
        }

        view.LunesCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (view.LunesCheck.isChecked){
                view.LunestimePicker.isClickable=false
                view.Lunestime.text="Apertura"
                view.LunestimePickerCierre.isClickable=false
                view.LunestimeCierre.text="Cierre"
            }else{
                view.LunestimePicker.isClickable=true
                view.LunestimePickerCierre.isClickable=true
            }
        }
        view.LunestimePicker.setOnClickListener {
            openTimePicker(view.Lunestime)
        }
        view.LunestimePickerCierre.setOnClickListener {
            openTimePicker(view.LunestimeCierre)
        }

        view.MartesCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (view.MartesCheck.isChecked){
                view.MartestimePicker.isClickable=false
                view.Martestime.text="Apertura"
                view.MartestimePickerCierre.isClickable=false
                view.MartestimeCierre.text="Cierre"
            }else{
                view.MartestimePicker.isClickable=true
                view.MartestimePickerCierre.isClickable=true
            }
        }
        view.MartestimePicker.setOnClickListener {
            openTimePicker(view.Martestime)
        }
        view.MartestimePickerCierre.setOnClickListener {
            openTimePicker(view.MartestimeCierre)
        }

        view.MiercolesCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (view.MiercolesCheck.isChecked){
                view.MiercolestimePicker.isClickable=false
                view.Miercolestime.text="Apertura"
                view.MiercolestimePickerCierre.isClickable=false
                view.MiercolestimeCierre.text="Cierre"
            }else{
                view.MiercolestimePicker.isClickable=true
                view.MiercolestimePickerCierre.isClickable=true
            }
        }
        view.MiercolestimePicker.setOnClickListener {
            openTimePicker(view.Miercolestime)
        }
        view.MiercolestimePickerCierre.setOnClickListener {
            openTimePicker(view.MiercolestimeCierre)
        }

        view.JuevesCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (view.JuevesCheck.isChecked){
                view.JuevestimePicker.isClickable=false
                view.Juevestime.text="Apertura"
                view.JuevestimePickerCierre.isClickable=false
                view.JuevestimeCierre.text="Cierre"
            }else{
                view.JuevestimePicker.isClickable=true
                view.JuevestimePickerCierre.isClickable=true
            }
        }
        view.JuevestimePicker.setOnClickListener {
            openTimePicker(view.Juevestime)
        }
        view.JuevestimePickerCierre.setOnClickListener {
            openTimePicker(view.JuevestimeCierre)
        }

        view.ViernesCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (view.ViernesCheck.isChecked){
                view.ViernestimePicker.isClickable=false
                view.Viernestime.text="Apertura"
                view.ViernestimePickerCierre.isClickable=false
                view.ViernestimeCierre.text="Cierre"
            }else{
                view.ViernestimePicker.isClickable=true
                view.ViernestimePickerCierre.isClickable=true
            }
        }
        view.ViernestimePicker.setOnClickListener {
            openTimePicker(view.Viernestime)
        }
        view.ViernestimePickerCierre.setOnClickListener {
            openTimePicker(view.ViernestimeCierre)
        }

        view.SabadoCheck.setOnCheckedChangeListener { compoundButton, b ->
            if (view.SabadoCheck.isChecked){
                view.SabadotimePicker.isClickable=false
                view.Sabadotime.text="Apertura"
                view.SabadotimePickerCierre.isClickable=false
                view.SabadotimeCierre.text="Cierre"
            }else{
                view.SabadotimePicker.isClickable=true
                view.SabadotimePickerCierre.isClickable=true
            }
        }
        view.SabadotimePicker.setOnClickListener {
            openTimePicker(view.Sabadotime)
        }
        view.SabadotimePickerCierre.setOnClickListener {
            openTimePicker(view.SabadotimeCierre)
        }

        return view
    }
    fun limpiarHorarios(){
        DomingoCheck.isChecked=false
        LunesCheck.isChecked=false
        MartesCheck.isChecked=false
        MiercolesCheck.isChecked=false
        JuevesCheck.isChecked=false
        ViernesCheck.isChecked=false
        SabadoCheck.isChecked=false
        Domingotime.text="Apertura"
        Lunestime.text="Apertura"
        Martestime.text="Apertura"
        Miercolestime.text="Apertura"
        Juevestime.text="Apertura"
        Viernestime.text="Apertura"
        Sabadotime.text="Apertura"
        DomingotimeCierre.text="Cierre"
        LunestimeCierre.text="Cierre"
        MartestimeCierre.text="Cierre"
        MiercolestimeCierre.text="Cierre"
        JuevestimeCierre.text="Cierre"
        ViernestimeCierre.text="Cierre"
        SabadotimeCierre.text="Cierre"
    }
    private fun openTimePicker(label:TextView){
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
            label.text=salida.toString()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==111 && resultCode== Activity.RESULT_OK && data != null){
            filepath=data.data!!
        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        if (!place.name.isEmpty()){
                            nombre.setText(place.name)
                        }
                        if (!place.address.isEmpty()){
                            ubicacion.setText(place.address)
                        }
                        if (place.openingHours != null){
                            var horarios:String=place.openingHours.weekdayText.toString()
                            horarios=horarios.substring(1,horarios.length-1)
                            val horariosArray= horarios.split(", ")
                            for (i in horariosArray.indices){
                                if (horariosArray[i].contains("domingo")){
                                    var aux= horariosArray[i].split(": ")
                                    if (aux[1].contains("Abierto las 24 horas")){
                                        DomingoCheck.isChecked=true
                                    }else{
                                        aux=aux[1].split("–")
                                        Domingotime.text=aux.get(0)
                                        DomingotimeCierre.text=aux.get(1)
                                    }
                                }
                                if (horariosArray[i].contains("lunes")){
                                    var aux= horariosArray[i].split(": ")
                                    if (aux[1].contains("Abierto las 24 horas")){
                                        LunesCheck.isChecked=true
                                    }else{
                                        aux=aux[1].split("–")
                                        Lunestime.text=aux.get(0)
                                        LunestimeCierre.text=aux.get(1)
                                    }
                                }
                                if (horariosArray[i].contains("martes")){
                                    var aux= horariosArray[i].split(": ")
                                    if (aux[1].contains("Abierto las 24 horas")){
                                        MartesCheck.isChecked=true
                                    }else{
                                        aux=aux[1].split("–")
                                        Martestime.text=aux.get(0)
                                        MartestimeCierre.text=aux.get(1)
                                    }
                                }
                                if (horariosArray[i].contains("miércoles")){
                                    var aux= horariosArray[i].split(": ")
                                    if (aux[1].contains("Abierto las 24 horas")){
                                        MiercolesCheck.isChecked=true
                                    }else{
                                        aux=aux[1].split("–")
                                        Miercolestime.text=aux.get(0)
                                        MiercolestimeCierre.text=aux.get(1)
                                    }
                                }
                                if (horariosArray[i].contains("jueves")){
                                    var aux= horariosArray[i].split(": ")
                                    if (aux[1].contains("Abierto las 24 horas")){
                                        JuevesCheck.isChecked=true
                                    }else{
                                        aux=aux[1].split("–")
                                        Juevestime.text=aux.get(0)
                                        JuevestimeCierre.text=aux.get(1)
                                    }
                                }
                                if (horariosArray[i].contains("viernes")){
                                    var aux= horariosArray[i].split(": ")
                                    if (aux[1].contains("Abierto las 24 horas")){
                                        ViernesCheck.isChecked=true
                                    }else{
                                        aux=aux[1].split("–")
                                        Viernestime.text=aux.get(0)
                                        ViernestimeCierre.text=aux.get(1)
                                    }
                                }
                                if (horariosArray[i].contains("sábado")){
                                    var aux= horariosArray[i].split(": ")
                                    if (aux[1].contains("Abierto las 24 horas")){
                                        SabadoCheck.isChecked=true
                                    }else{
                                        aux=aux[1].split("–")
                                        Sabadotime.text=aux.get(0)
                                        SabadotimeCierre.text=aux.get(1)
                                    }
                                }
                            }

                        }
                        ubicacionp= GeoPoint(place.latLng.latitude, place.latLng.longitude)

                        encontrado=true
                        borrar.visibility=View.VISIBLE
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage ?: "")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
    }
    fun prueba(){

        val fields= listOf(Place.Field.ID , Place.Field.NAME, Place.Field.OPENING_HOURS,Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(context)

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

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

    override fun showProgressDialogV() {
        verificar.visibility=View.GONE
        progressBar_verificar.visibility=View.VISIBLE
    }

    override fun hideProgressDialogV() {
        verificar.visibility=View.VISIBLE
        progressBar_verificar.visibility=View.GONE
    }

    override fun verificar() {
        val cedulaAdmin:String=etxt_idAdmin.editText?.text.toString().trim()
        if (presenter.checkEmptyCedulaAdmin(cedulaAdmin)){
            etxt_idAdmin.error="Ingrese un numero de cedula"
            verificado=null
            nombre_Admin.setText("")
            nombre_Admin.visibility=View.GONE
            return
        }else{
            showProgressDialogV()
            observeData(cedulaAdmin)
        }
    }

    override fun addParque() {
        val nombre:String=etxt_nombre.editText?.text.toString().trim()
        val ubicacion:String=etxt_ubicacion.editText?.text.toString().trim()
        val descripcion:String=etxt_descripcion.editText?.text.toString().trim()
        val cedulaAdmin:String=etxt_idAdmin.editText?.text.toString().trim()
        val aforomax: String = etxt_aforomax.editText?.text.toString().trim()
        val horarios: MutableList<String> = mutableListOf()


        if (presenter.checkEmptyNombre(nombre)){
            etxt_nombre.error="Ingrese el nombre el parque"
            return
        }
        if(presenter.checkEmptyUbicacion(ubicacion)){
            etxt_ubicacion.error="Ingrese la ubicacion del parque"
            return
        }

        if (presenter.checkEmptyDescripcion(descripcion)){
            etxt_descripcion.error="Ingrese la descripcion del parque"
            return
        }
        if (presenter.checkEmptyAforoMax(aforomax)){
            etxt_aforomax.error="Ingrese el aforo maximo del parque"
            return
        }
        if (tipoP==null){
            showError("Seleccione un tipo de parque")
            return
        }
        if (tipoP==null || tipoP.equals("Seleccione un tipo")){
            showError("Seleccione un tipo de parque")
            return
        }
        if (filepath==null){
            showError("Seleccione una imagen")
            return
        }

        if (DomingoCheck.isChecked){
            horarios.add(0, "Abierto las 24 horas")
        }else{
            val domingoApertura:String = Domingotime.text.toString()
            val domingoCierre:String = DomingotimeCierre.text.toString()
            if (domingoApertura != "Apertura" && domingoCierre != "Cierre"){
                if (presenter.checkHour(domingoApertura,domingoCierre)){
                    ErrorHorarios.text="La hora de apertura es mayor a la de cierre"
                    ErrorHorarios.visibility=View.VISIBLE
                    return
                }else{
                    horarios.add(0,domingoApertura+"-"+ domingoCierre)
                }
            }else if(domingoApertura == "Apertura" && domingoCierre != "Cierre" || domingoApertura != "Apertura" && domingoCierre == "Cierre"){
                ErrorHorarios.text="Verifique sus horarios"
                ErrorHorarios.visibility=View.VISIBLE
                return
            }else{
                horarios.add(0,"DEFAULT" )
            }
        }

        if (LunesCheck.isChecked){
            horarios.add(1, "Abierto las 24 horas")
        }else{
            val LunesApertura:String = Lunestime.text.toString()
            val LunesCierre:String = LunestimeCierre.text.toString()
            if (LunesApertura != "Apertura" && LunesCierre != "Cierre"){
                if (presenter.checkHour(LunesApertura,LunesCierre)){
                    ErrorHorarios.text="La hora de apertura es mayor a la de cierre"
                    ErrorHorarios.visibility=View.VISIBLE
                    return
                }else{
                    horarios.add(1,LunesApertura+"-"+ LunesCierre)
                }
            }else if(LunesApertura == "Apertura" && LunesCierre != "Cierre" || LunesApertura != "Apertura" && LunesCierre == "Cierre"){
                ErrorHorarios.text="Verifique sus horarios"
                ErrorHorarios.visibility=View.VISIBLE
                return
            }else{
                horarios.add(1,"DEFAULT" )
            }
        }
        if (MartesCheck.isChecked){
            horarios.add(2, "Abierto las 24 horas")
        }else{
            val MartesApertura:String = Martestime.text.toString()
            val MartesCierre:String = MartestimeCierre.text.toString()
            if (MartesApertura != "Apertura" && MartesCierre != "Cierre"){
                if (presenter.checkHour(MartesApertura,MartesCierre)){
                    ErrorHorarios.text="La hora de apertura es mayor a la de cierre"
                    ErrorHorarios.visibility=View.VISIBLE
                    return
                }else{
                    horarios.add(2,MartesApertura+"-"+ MartesCierre)
                }
            }else if(MartesApertura == "Apertura" && MartesCierre != "Cierre" || MartesApertura != "Apertura" && MartesCierre == "Cierre"){
                ErrorHorarios.text="Verifique sus horarios"
                ErrorHorarios.visibility=View.VISIBLE
                return
            }else{
                horarios.add(2,"DEFAULT" )
            }
        }
        if (MiercolesCheck.isChecked){
            horarios.add(3, "Abierto las 24 horas")
        }else{
            val MiercolesApertura:String = Miercolestime.text.toString()
            val MiercolesCierre:String = MiercolestimeCierre.text.toString()
            if (MiercolesApertura != "Apertura" && MiercolesCierre != "Cierre"){
                if (presenter.checkHour(MiercolesApertura,MiercolesCierre)){
                    ErrorHorarios.text="La hora de apertura es mayor a la de cierre"
                    ErrorHorarios.visibility=View.VISIBLE
                    return
                }else{
                    horarios.add(3,MiercolesApertura+"-"+ MiercolesCierre)
                }
            }else if(MiercolesApertura == "Apertura" && MiercolesCierre != "Cierre" || MiercolesApertura != "Apertura" && MiercolesCierre == "Cierre"){
                ErrorHorarios.text="Verifique sus horarios"
                ErrorHorarios.visibility=View.VISIBLE
                return
            }else{
                horarios.add(3,"DEFAULT" )
            }
        }
        if (JuevesCheck.isChecked){
            horarios.add(4, "Abierto las 24 horas")
        }else{
            val JuevesApertura:String = Juevestime.text.toString()
            val JuevesCierre:String = JuevestimeCierre.text.toString()
            if (JuevesApertura != "Apertura" && JuevesCierre != "Cierre"){
                if (presenter.checkHour(JuevesApertura,JuevesCierre)){
                    ErrorHorarios.text="La hora de apertura es mayor a la de cierre"
                    ErrorHorarios.visibility=View.VISIBLE
                    return
                }else{
                    horarios.add(4,JuevesApertura+"-"+ JuevesCierre)
                }
            }else if(JuevesApertura == "Apertura" && JuevesCierre != "Cierre" || JuevesApertura != "Apertura" && JuevesCierre == "Cierre"){
                ErrorHorarios.text="Verifique sus horarios"
                ErrorHorarios.visibility=View.VISIBLE
                return
            }else{
                horarios.add(4,"DEFAULT" )
            }
        }
        if (ViernesCheck.isChecked){
            horarios.add(5, "Abierto las 24 horas")
        }else{
            val ViernesApertura:String = Viernestime.text.toString()
            val ViernesCierre:String = ViernestimeCierre.text.toString()
            if (ViernesApertura != "Apertura" && ViernesCierre != "Cierre"){
                if (presenter.checkHour(ViernesApertura,ViernesCierre)){
                    ErrorHorarios.text="La hora de apertura es mayor a la de cierre"
                    ErrorHorarios.visibility=View.VISIBLE
                    return
                }else{
                    horarios.add(5,ViernesApertura+"-"+ ViernesCierre)
                }
            }else if(ViernesApertura == "Apertura" && ViernesCierre != "Cierre" || ViernesApertura != "Apertura" && ViernesCierre == "Cierre"){
                ErrorHorarios.text="Verifique sus horarios"
                ErrorHorarios.visibility=View.VISIBLE
                return
            }else{
                horarios.add(5,"DEFAULT" )
            }
        }
        if (SabadoCheck.isChecked){
            horarios.add(6, "Abierto las 24 horas")
        }else{
            val SabadoApertura:String = Sabadotime.text.toString()
            val SabadoCierre:String = SabadotimeCierre.text.toString()
            if (SabadoApertura != "Apertura" && SabadoCierre != "Cierre"){
                if (presenter.checkHour(SabadoApertura,SabadoCierre)){
                    ErrorHorarios.text="La hora de apertura es mayor a la de cierre"
                    ErrorHorarios.visibility=View.VISIBLE
                    return
                }else{
                    horarios.add(6,SabadoApertura+"-"+ SabadoCierre)
                }
            }else if(SabadoApertura == "Apertura" && SabadoCierre != "Cierre" || SabadoApertura != "Apertura" && SabadoCierre == "Cierre"){
                ErrorHorarios.text="Verifique sus horarios"
                ErrorHorarios.visibility=View.VISIBLE
                return
            }else{
                horarios.add(6,"DEFAULT" )
            }
        }

        val logHorarios = horarios.filter { x-> x.equals("DEFAULT") }.size

        if (logHorarios==7){
            showError("Seleccione por lo menos un horario")
            return
        }
        var horarioFinal:String=""
        for (i in horarios.indices){
            if (horarios[i] != "DEFAULT"){
                when(i){
                    0 -> horarioFinal= "domingo: "+horarios[i]+","
                    1 -> horarioFinal= horarioFinal+" lunes: "+horarios[i]+","
                    2 -> horarioFinal= horarioFinal+" martes: "+horarios[i]+","
                    3 -> horarioFinal= horarioFinal+" miercoles: "+horarios[i]+","
                    4 -> horarioFinal= horarioFinal+" jueves: "+horarios[i]+","
                    5 -> horarioFinal= horarioFinal+" viernes: "+horarios[i]+","
                    6 -> horarioFinal= horarioFinal+" sabado: "+horarios[i]+","
                }
            }
        }

        horarioFinal=horarioFinal.substring(0,horarioFinal.length-1)

        var parque=Parque()

        if (!presenter.checkEmptyCedulaAdmin(cedulaAdmin) && verificado==null){
            verificar()
            if (verificado!=null){
                parque.idAdmin=verificado!!.id
                parque.cedula=verificado!!.cedula
                parque.nombreAdmin= verificado!!.nombre
            }
        }
        if (verificado!=null){
            parque.idAdmin=verificado!!.id
            parque.cedula=verificado!!.cedula
            parque.nombreAdmin=verificado!!.nombre
        }
        parque.nombre=nombre
        parque.tipo=tipoP!!
        parque.ubicacion=ubicacion
        parque.horario=horarioFinal
        parque.horarios= horarios
        parque.descripcion=descripcion
        parque.aforoMaximo=aforomax
        parque.calificacion="0.0"
        parque.locali=ubicacionp!!

        presenter.addParque(parque, filepath)
    }

    fun observeData(cedula:String){
        viewModel.fetchDataUser(cedula).observe(viewLifecycleOwner, Observer {
            hideProgressDialogV()
            if (!it.isEmpty()){
                var user: MutableList<Users>? =it
                etxt_idAdmin.error=""
                nombre_Admin.text= user?.get(0)?.nombre
                nombre_Admin.visibility=View.VISIBLE
                verificado=user?.get(0)
                if(verificado?.rol!="USER"){
                    etxt_idAdmin.error="El usuario ya es administrador"
                    verificado=null
                }
            }else{
                nombre_Admin.setText("")
                nombre_Admin.visibility=View.GONE
                verificado=null
                etxt_idAdmin.error="El numero de cedula no existe"
            }

        })
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
        nombre.setText("")
        ubicacion.setText("")
        aforomax.setText("")
        descripcion.setText("")
        idAdmin.setText("")
        nombre_Admin.setText("")
        ErrorHorarios.visibility=View.GONE
        Toast.makeText(context,"Parque guardado correctamente", Toast.LENGTH_SHORT).show()

    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}