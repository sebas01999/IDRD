package com.example.idrd.presentation.agregarParque.view

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.TiposParque
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.crudParques.CrudParqueInteractorImpl
import com.example.idrd.presentation.agregarParque.AgregarParqueContract
import com.example.idrd.presentation.agregarParque.AgregarParquePresenter.AgregarParquePresenter
import com.example.idrd.presentation.agregarParque.model.UserViewModel
import com.example.idrd.presentation.inicio.model.TiposViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_agregar_parque.*
import kotlinx.android.synthetic.main.fragment_agregar_parque.view.*
import kotlinx.android.synthetic.main.fragment_form.*


class agregarParqueFragment : DialogFragment(), AgregarParqueContract.AgregarView {
    lateinit var filepath : Uri
    lateinit var presenter: AgregarParquePresenter
    var encontrado=false
    var verificado:Users?=null
    var tipoP: String?=null
    var ubicacionp:GeoPoint?=null
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
            horario.setText("")
            descripcion.setText("")
            borrar.visibility=View.GONE
            encontrado=false
        }
        return view
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
                            var horarios=place.openingHours.weekdayText

                            horario.setText(horarios.toString())
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

        val fields= listOf(Place.Field.ID , Place.Field.NAME, Place.Field.OPENING_HOURS,Place.Field.ADDRESS, Place.Field.LAT_LNG)
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
        val horario: String= etxt_horario.editText?.text.toString().trim()
        val descripcion:String=etxt_descripcion.editText?.text.toString().trim()
        val cedulaAdmin:String=etxt_idAdmin.editText?.text.toString().trim()
        if (presenter.checkEmptyNombre(nombre)){
            etxt_nombre.error="Ingrese el nombre el parque"
            return
        }
        if(presenter.checkEmptyUbicacion(ubicacion)){
            etxt_ubicacion.error="Ingrese la ubicacion del parque"
            return
        }
        if (presenter.checkEmptyHour(horario)){
            etxt_horario.error="Ingrese el horario del parque"
            return
        }
        if (presenter.checkEmptyDescripcion(descripcion)){
            etxt_descripcion.error="Ingrese la descripcion del parque"
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
        parque.horario=horario
        parque.descripcion=descripcion
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
        horario.setText("")
        descripcion.setText("")
        idAdmin.setText("")
        nombre_Admin.setText("")
        Toast.makeText(context,"Parque guardado correctamente", Toast.LENGTH_SHORT).show()

    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}