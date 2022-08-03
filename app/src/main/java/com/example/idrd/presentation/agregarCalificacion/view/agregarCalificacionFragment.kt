package com.example.idrd.presentation.agregarCalificacion.view

import android.graphics.Color
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.model.Calificacion
import com.example.idrd.data.model.Parque
import com.example.idrd.domain.interactor.calificacionInteractor.CalificacionInteractorImpl
import com.example.idrd.presentation.agregarCalificacion.AgregarCalificacionContract
import com.example.idrd.presentation.agregarCalificacion.ArgregarCalificacionPresenter.AgregarCalificacionPresenter
import com.example.idrd.presentation.agregarCalificacion.model.CalificacionUserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_agregar_calificacion.*
import kotlinx.android.synthetic.main.fragment_agregar_calificacion.view.*

class agregarCalificacionFragment : BottomSheetDialogFragment(), AgregarCalificacionContract.AgregarCalificacionView {
    lateinit var presenter:AgregarCalificacionPresenter
    private val viewModel by lazy { ViewModelProvider(this).get(CalificacionUserViewModel::class.java) }
    private var calificacion:Calificacion?=null
    var estrellasI:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_agregar_calificacion, container, false)
        presenter= AgregarCalificacionPresenter((CalificacionInteractorImpl()))
        presenter.attachView(this)
        view.unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
        observeData()
        view.unaEstrella.setOnClickListener{
            estrellasI=1
            unaEstrella()
        }
        view.dosEstrella.setOnClickListener {
            estrellasI=2
            dosEstrella()
        }
        view.tresEstrella.setOnClickListener {
            estrellasI=3
            tresEstrella()
        }
        view.cuatroEstrella.setOnClickListener {
            estrellasI=4
            cuatroEstrella()
        }
        view.cincoEstrella.setOnClickListener {
            estrellasI=5
            cincoEstrella()
        }
        view.envcCalificar.setOnClickListener{
            addCalificacion()
        }

        return view
    }
    fun limpiar(){
        unaEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
        dosEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
        tresEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
        cuatroEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
        cincoEstrella.setColorFilter(Color.parseColor("#EBF0FF"))
    }

    fun observeData(){
        val auth= FirebaseAuth.getInstance()
        if (arguments!=null){
            val parque: Parque = arguments?.getSerializable("parque") as Parque
            viewModel.fetchCalificacionesDataUser(auth.currentUser?.uid.toString(), parque.id).observe(viewLifecycleOwner, Observer {
                if (!it.isEmpty()){
                    calificacion=it.get(0)
                    comentario.setText(it.get(0).comentario)
                    if (it.get(0).estrellas==1){
                        unaEstrella()
                    }else if (it.get(0).estrellas==2){
                        dosEstrella()
                    }else if (it.get(0).estrellas==3){
                        tresEstrella()
                    }else if (it.get(0).estrellas==4){
                        cuatroEstrella()
                    }else if (it.get(0).estrellas==5){
                        cincoEstrella()
                    }
                }
            })
        }
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        envcCalificar.visibility=View.GONE
        progressBar_Calificacion.visibility=View.VISIBLE
    }

    override fun hideProgressDialog() {
        envcCalificar.visibility=View.VISIBLE
        progressBar_Calificacion.visibility=View.GONE
    }

    override fun addCalificacion() {
        val comentario:String=etxt_comentario.editText?.text.toString().trim()

        if (presenter.checkEmptyComent(comentario)){
            etxt_comentario.error="Ingrese un comentario"
            return
        }
        if (calificacion!=null){
            calificacion!!.estrellas=estrellasI
            calificacion!!.comentario=comentario
            presenter.editCalificacion(calificacion!!)
        }else{
            if (arguments!=null) {
                val parque: Parque = arguments?.getSerializable("parque") as Parque
                var calificacion = Calificacion()
                calificacion.comentario = comentario
                calificacion.estrellas = estrellasI
                calificacion.idParque=parque.id
                presenter.addCalificacion(calificacion)
            }
        }

    }

    override fun unaEstrella() {
        limpiar()
        estrellas.text="1/5"
        unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
    }

    override fun dosEstrella() {
        limpiar()
        estrellas.text="2/5"
        unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
        dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
    }

    override fun tresEstrella() {
        limpiar()
        estrellas.text="3/5"
        unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
        dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
        tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
    }

    override fun cuatroEstrella() {
        limpiar()
        estrellas.text="4/5"
        unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
        dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
        tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
        cuatroEstrella.setColorFilter(Color.parseColor("#FFC833"))
    }

    override fun cincoEstrella() {
        limpiar()
        estrellas.text="5/5"
        unaEstrella.setColorFilter(Color.parseColor("#FFC833"))
        dosEstrella.setColorFilter(Color.parseColor("#FFC833"))
        tresEstrella.setColorFilter(Color.parseColor("#FFC833"))
        cuatroEstrella.setColorFilter(Color.parseColor("#FFC833"))
        cincoEstrella.setColorFilter(Color.parseColor("#FFC833"))
    }

    override fun showSuccess() {
        limpiar()
        unaEstrella()
        comentario.setText("")
        Toast.makeText(context,"Calificacion guardada correctamente", Toast.LENGTH_SHORT).show()
    }

}