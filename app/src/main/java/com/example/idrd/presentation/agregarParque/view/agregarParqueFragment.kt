package com.example.idrd.presentation.agregarParque.view

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_agregar_parque.*
import kotlinx.android.synthetic.main.fragment_agregar_parque.view.*
import kotlinx.android.synthetic.main.fragment_form.*


class agregarParqueFragment : DialogFragment(), AgregarParqueContract.AgregarView {
    lateinit var filepath : Uri
    lateinit var presenter: AgregarParquePresenter
    var verificado:Users?=null
    private val viewModel by lazy { ViewModelProvider(this).get(UserViewModel::class.java) }
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
        val tipos = resources.getStringArray(R.array.tipos)
        val adapter= context?.let {
            ArrayAdapter(
                it,
                R.layout.item_tipos_parque,
                tipos
            )
        }

        with(view.drop_items){
            setAdapter(adapter)
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
        return view
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
        val tipo: String=drop_items.text.toString()
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
        if (filepath==null){
            showError("Seleccione una imagen")
            return
        }
        var parque=Parque()

        if (!presenter.checkEmptyCedulaAdmin(cedulaAdmin) && verificado==null){
            verificar()
            if (verificado!=null){
                parque.idAdmin=cedulaAdmin
                parque.nombreAdmin= (verificado!!.nombre)
            }
        }
        if (verificado!=null){
            parque.idAdmin=verificado!!.cedula
            parque.nombreAdmin=verificado!!.nombre
        }
        parque.nombre=nombre
        parque.tipo=tipo
        parque.ubicacion=ubicacion
        parque.horario=horario
        parque.descripcion=descripcion
        parque.calificacion="0.0"

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