package com.example.idrd.presentation.crud_parques.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Users
import com.example.idrd.domain.interactor.crudParques.CrudParqueInteractorImpl
import com.example.idrd.presentation.acceder_solicitudes.view.fragment_acceder_solicitudes
import com.example.idrd.presentation.agregarParque.model.UserViewModel
import com.example.idrd.presentation.agregarParque.view.agregarParqueFragment
import com.example.idrd.presentation.crud_parques.CrudParqueContract
import com.example.idrd.presentation.crud_parques.CrudParquesPresenter.CrudParquesPresenter
import com.example.idrd.presentation.inicio.model.MainViewModel
import com.example.idrd.presentation.inicio.model.TiposViewModel
import com.example.idrd.presentation.inicio.view.MainAdapter
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.fragment_crud_parques.*
import kotlinx.android.synthetic.main.fragment_crud_parques.DomingoCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.Domingotime
import kotlinx.android.synthetic.main.fragment_crud_parques.DomingotimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.ErrorHorarios
import kotlinx.android.synthetic.main.fragment_crud_parques.JuevesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.Juevestime
import kotlinx.android.synthetic.main.fragment_crud_parques.JuevestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.LunesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.Lunestime
import kotlinx.android.synthetic.main.fragment_crud_parques.LunestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.MartesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.Martestime
import kotlinx.android.synthetic.main.fragment_crud_parques.MartestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.MiercolesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.Miercolestime
import kotlinx.android.synthetic.main.fragment_crud_parques.MiercolestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.SabadoCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.Sabadotime
import kotlinx.android.synthetic.main.fragment_crud_parques.SabadotimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.ViernesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.Viernestime
import kotlinx.android.synthetic.main.fragment_crud_parques.ViernestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.aforomax
import kotlinx.android.synthetic.main.fragment_crud_parques.cancelar
import kotlinx.android.synthetic.main.fragment_crud_parques.descripcion
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_aforomax
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_descripcion
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_idAdmin

import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_nombre
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_ubicacion
import kotlinx.android.synthetic.main.fragment_crud_parques.idAdmin

import kotlinx.android.synthetic.main.fragment_crud_parques.mostrarParque
import kotlinx.android.synthetic.main.fragment_crud_parques.nombre
import kotlinx.android.synthetic.main.fragment_crud_parques.nombre_Admin
import kotlinx.android.synthetic.main.fragment_crud_parques.progressBar_verificar
import kotlinx.android.synthetic.main.fragment_crud_parques.spiner
import kotlinx.android.synthetic.main.fragment_crud_parques.ubicacion
import kotlinx.android.synthetic.main.fragment_crud_parques.verificar
import kotlinx.android.synthetic.main.fragment_crud_parques.view.*
import kotlinx.android.synthetic.main.fragment_crud_parques.view.DomingoCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.view.Domingotime
import kotlinx.android.synthetic.main.fragment_crud_parques.view.DomingotimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.DomingotimePicker
import kotlinx.android.synthetic.main.fragment_crud_parques.view.DomingotimePickerCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.JuevesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.view.Juevestime
import kotlinx.android.synthetic.main.fragment_crud_parques.view.JuevestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.JuevestimePicker
import kotlinx.android.synthetic.main.fragment_crud_parques.view.JuevestimePickerCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.LunesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.view.Lunestime
import kotlinx.android.synthetic.main.fragment_crud_parques.view.LunestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.LunestimePicker
import kotlinx.android.synthetic.main.fragment_crud_parques.view.LunestimePickerCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MartesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.view.Martestime
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MartestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MartestimePicker
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MartestimePickerCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MiercolesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.view.Miercolestime
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MiercolestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MiercolestimePicker
import kotlinx.android.synthetic.main.fragment_crud_parques.view.MiercolestimePickerCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.SabadoCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.view.Sabadotime
import kotlinx.android.synthetic.main.fragment_crud_parques.view.SabadotimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.SabadotimePicker
import kotlinx.android.synthetic.main.fragment_crud_parques.view.SabadotimePickerCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.ViernesCheck
import kotlinx.android.synthetic.main.fragment_crud_parques.view.Viernestime
import kotlinx.android.synthetic.main.fragment_crud_parques.view.ViernestimeCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.ViernestimePicker
import kotlinx.android.synthetic.main.fragment_crud_parques.view.ViernestimePickerCierre
import kotlinx.android.synthetic.main.fragment_crud_parques.view.agregar_foto
import kotlinx.android.synthetic.main.fragment_crud_parques.view.cancelar
import kotlinx.android.synthetic.main.fragment_crud_parques.view.rv
import kotlinx.android.synthetic.main.fragment_crud_parques.view.searchView
import kotlinx.android.synthetic.main.fragment_crud_parques.view.spiner
import kotlinx.android.synthetic.main.fragment_crud_parques.view.verificar
import java.text.SimpleDateFormat


class CrudParquesFragment : Fragment(), MainAdapter.OnItemClickListener, CrudParqueContract.CrudView {
    private lateinit var adapter: MainAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val viewModelUser by lazy { ViewModelProvider(this).get(UserViewModel::class.java) }
    private val viewModelTipos by lazy { ViewModelProvider(this).get(TiposViewModel::class.java) }
    private var parque:Parque?= null
    var tipoP: String?=null
    var filepath : Uri?=null
    var verificado:Users?=null
    private var dataListTipos = mutableListOf<String>()
    lateinit var presenter: CrudParquesPresenter
    private var dataList = mutableListOf<Parque>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_crud_parques, container, false)
        presenter= CrudParquesPresenter(CrudParqueInteractorImpl())
        presenter.attachView(this)
        adapter= context?.let { MainAdapter(it, this) }!!
        view.rv.layoutManager=LinearLayoutManager(context)
        view.rv.adapter=adapter
        observeData()

        viewModelTipos.fetchTiposParqueData().observe(viewLifecycleOwner, Observer { lista ->
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
        view.searchView.clearFocus()
        view.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        view.cancelar.setOnClickListener {
            parque=null
            cancelarV()
        }
        view.agregar_foto.setOnClickListener {
            addFoto()
        }
        view.verificar.setOnClickListener {
            verificar()
        }

        view.toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.menu_agregar -> {
                    val dialog=agregarParqueFragment()
                    //dialog.dialog?.window?.setLayout(500, 500)
                    dialog.show(childFragmentManager, "SimpleDialog")
                    true
                }
                R.id.menu_modificar -> {
                    editar()
                    true
                }
                else -> false
            }
        }

        view.spiner.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tipoP=dataListTipos.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

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
    private fun openTimePicker(label: TextView){
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
    override fun onItemClick(item: Parque) {
        limpiarHorarios()
        parque=null
        parque=item
        var list:List<String> = emptyList()
        nombre.setText(parque!!.nombre)
        ubicacion.setText(parque!!.ubicacion)
        aforomax.setText(parque!!.aforoMaximo)
        for (i in parque!!.horarios.indices){
            if (parque!!.horarios[i] != "DEFAULT"){

                when(i){
                    0 -> if (parque!!.horarios[i]=="Abierto las 24 horas"){
                        DomingoCheck.isChecked=true
                    }else{
                        list = parque!!.horarios[i].split("-")
                        Domingotime.text = list.get(0)
                        DomingotimeCierre.text = list.get(1)
                    }
                    1 -> if (parque!!.horarios[i]=="Abierto las 24 horas"){
                        LunesCheck.isChecked=true
                    }else{
                        list = parque!!.horarios[i].split("-")
                        Lunestime.text = list.get(0)
                        LunestimeCierre.text = list.get(1)
                    }
                    2 -> if (parque!!.horarios[i]=="Abierto las 24 horas"){
                        MartesCheck.isChecked=true
                    }else{
                        list = parque!!.horarios[i].split("-")
                        Martestime.text = list.get(0)
                        MartestimeCierre.text = list.get(1)
                    }
                    3 -> if (parque!!.horarios[i]=="Abierto las 24 horas"){
                        MiercolesCheck.isChecked=true
                    }else{
                        list = parque!!.horarios[i].split("-")
                        Miercolestime.text = list.get(0)
                        MiercolestimeCierre.text = list.get(1)
                    }
                    4 -> if (parque!!.horarios[i]=="Abierto las 24 horas"){
                        JuevesCheck.isChecked=true
                    }else{
                        list = parque!!.horarios[i].split("-")
                        Juevestime.text = list.get(0)
                        JuevestimeCierre.text = list.get(1)
                    }
                    5 -> if (parque!!.horarios[i]=="Abierto las 24 horas"){
                        ViernesCheck.isChecked=true
                    }else{
                        list = parque!!.horarios[i].split("-")
                        Viernestime.text = list.get(0)
                        ViernestimeCierre.text = list.get(1)
                    }
                    6 -> if (parque!!.horarios[i]=="Abierto las 24 horas"){
                        SabadoCheck.isChecked=true
                    }else{
                        list = parque!!.horarios[i].split("-")
                        Sabadotime.text = list.get(0)
                        SabadotimeCierre.text = list.get(1)
                    }
                }

            }
        }
        descripcion.setText(parque!!.descripcion)
        spiner.setSelection(dataListTipos.indexOf(parque!!.tipo))
        tipoP=parque!!.tipo
        swactivo.isChecked = parque!!.activo

        if (parque!!.idAdmin!="DEFAULT IDADMIN"){
            idAdmin.setText(parque!!.cedula)
            nombre_Admin.text=parque!!.nombreAdmin
            nombre_Admin.visibility=View.VISIBLE
        }else{
            idAdmin.setText("")
            nombre_Admin.setText("")
            nombre_Admin.visibility=View.GONE
        }
        mostrarParque.visibility= View.VISIBLE
    }

    fun observeData(){
        //shimmer_view_container.startShimmer()
        viewModel.fetchParqueData().observe(viewLifecycleOwner , Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility=View.GONE
            dataList=it
            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })

    }
    fun filterList(text: String?) {
        var filterList = mutableListOf<Parque>()
        for (parque in dataList){
            if (text != null) {
                if (parque.nombre.toLowerCase().contains(text.toLowerCase())){
                    filterList.add(parque)
                }
            }
        }
        if (filterList.isEmpty()){
            rv.visibility=View.GONE
            noEncontrado.visibility=View.VISIBLE
        }else{
            noEncontrado.visibility=View.GONE
            adapter.setFilterList(filterList)
            rv.visibility=View.VISIBLE
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
            if (parque!=null){
                if (parque!!.cedula!=cedulaAdmin){
                    showProgressDialogV()
                    observeDataV(cedulaAdmin)
                }
            }
        }
    }
    fun observeDataV(cedula:String){
        viewModelUser.fetchDataUser(cedula).observe(viewLifecycleOwner, Observer {
            hideProgressDialogV()
            if (!it.isEmpty()){
                var user: MutableList<Users>?=it
                etxt_idAdmin.error=""
                nombre_Admin.text=user?.get(0)?.nombre
                nombre_Admin.visibility=View.VISIBLE
                verificado=user?.get(0)
                if (verificado?.rol!="USER"){
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

    override fun editar() {
        if(parque!=null){
            val nombre:String= etxt_nombre.editText?.text.toString().trim()
            val ubicacion:String= etxt_ubicacion.editText?.text.toString().trim()
            val horarios: MutableList<String> = mutableListOf()
            val descripcion:String= etxt_descripcion.editText?.text.toString().trim()
            val cedulaAdmin:String=etxt_idAdmin.editText?.text.toString().trim()
            val aforomax: String = etxt_aforomax.editText?.text.toString().trim()


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
            if (presenter.checkEmptyCedulaAdmin(cedulaAdmin)){
                verificado=null
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


            var parqueEnv=Parque()
            parqueEnv.idAdmin=parque!!.idAdmin
            if (parque!!.cedula!=cedulaAdmin){
                if (verificado!=null){
                    if (!parque!!.idAdmin.equals("DEFAULT IDADMIN")){

                        presenter.editParqueQuitarAdmin(parqueEnv)

                        parque!!.idAdmin=verificado!!.id
                        parque!!.cedula=verificado!!.cedula
                        parque!!.nombreAdmin=verificado!!.nombre
                        presenter.editParqueCambiarAdmin(parque!!)
                    }else{
                        parque!!.idAdmin=verificado!!.id
                        parque!!.cedula=verificado!!.cedula
                        parque!!.nombreAdmin=verificado!!.nombre
                        presenter.editParqueCambiarAdmin(parque!!)
                    }
                }else{
                    if (parque!!.idAdmin!="DEFAULT IDADMIN"){
                        presenter.editParqueQuitarAdmin(parqueEnv)
                        parque!!.idAdmin= "DEFAULT IDADMIN"
                        parque!!.cedula="DEFAULT CEDULA"
                        parque!!.nombreAdmin="DEFAULT NOMBRE ADMIN"

                    }
                }
            }
            parque!!.activo=swactivo.isChecked
            parque!!.nombre=nombre
            parque!!.tipo=tipoP!!
            parque!!.ubicacion=ubicacion
            parque!!.horario = horarioFinal
            parque!!.horarios = horarios
            parque!!.descripcion=descripcion
            parque!!.aforoMaximo=aforomax

            if (filepath==null){
                presenter.editar(parque!!)
            }else{
                presenter.editarFoto(parque!!,filepath!!)
            }


            observeData()
            parque=null
            verificado=null
        }



    }

    override fun cancelarV() {
        mostrarParque.visibility= View.GONE
        parque=null
        verificado=null
    }

    override fun addFoto() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "imagen"), 111)
    }

    override fun showSuccess(msgSuccess: String?) {
        nombre.setText("")
        ubicacion.setText("")
        aforomax.setText("")
        ErrorHorarios.visibility=View.GONE
        descripcion.setText("")
        idAdmin.setText("")
        parque=null
        verificado=null
        Toast.makeText(context,msgSuccess, Toast.LENGTH_SHORT).show()
        mostrarParque.visibility= View.GONE

    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }
}