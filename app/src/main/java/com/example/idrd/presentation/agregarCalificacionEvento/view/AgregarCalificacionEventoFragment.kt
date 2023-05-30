package com.example.idrd.presentation.agregarCalificacionEvento.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.data.model.CalificacionEvento
import com.example.idrd.data.model.Evento
import com.example.idrd.domain.interactor.calificacion_eventosInteractor.CalificacionEventoInteractorImpl
import com.example.idrd.presentation.agregarCalificacionEvento.AgregarCalificacionEventoContract
import com.example.idrd.presentation.agregarCalificacionEvento.AgregarCalificacionEventoPresenter.AgregarCalificacionEventoPresenter
import com.example.idrd.presentation.agregarCalificacionEvento.model.CalificacionEventoUserViewModel
import com.example.idrd.presentation.calificarEvento.model.CalificacionEventoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.cincoEstrella
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.comentario
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.cuatroEstrella
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.dosEstrella
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.envcCalificar
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.estrellas
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.etxt_comentario
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.progressBar_Calificacion
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.tresEstrella
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.unaEstrella
import kotlinx.android.synthetic.main.fragment_agregar_calificacion_evento.view.*


class AgregarCalificacionEventoFragment : BottomSheetDialogFragment(),AgregarCalificacionEventoContract.AgregarCalificacionEventoView {

    lateinit var presenter:AgregarCalificacionEventoPresenter
    private val viewModel by lazy { ViewModelProvider(this).get(CalificacionEventoUserViewModel::class.java) }
    private val viewModelTodas by lazy { ViewModelProvider(this).get(CalificacionEventoViewModel::class.java)}
    private var calificacion:CalificacionEvento?=null
    val color1="#EBF0FF"
    val color2="#FFC833"
    var estrellasI:Int=1
    var calificacionTotal=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater!!.inflate(R.layout.fragment_agregar_calificacion_evento, container, false)
        presenter= AgregarCalificacionEventoPresenter((CalificacionEventoInteractorImpl()))
        presenter.attachView(this)
        view.unaEstrella.setColorFilter(Color.parseColor(color2))
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
        unaEstrella.setColorFilter(Color.parseColor(color1))
        dosEstrella.setColorFilter(Color.parseColor(color1))
        tresEstrella.setColorFilter(Color.parseColor(color1))
        cuatroEstrella.setColorFilter(Color.parseColor(color1))
        cincoEstrella.setColorFilter(Color.parseColor(color1))
    }

    fun observeData(){
        val auth= FirebaseAuth.getInstance()
        if (arguments!=null){
            val evento: Evento = arguments?.getSerializable("evento") as Evento
            viewModel.fetchCalificacionesEventosDataUser(auth.currentUser?.uid.toString(), evento.id).observe(viewLifecycleOwner, Observer {
                if (!it.isEmpty()){
                    calificacion=it.get(0)
                    estrellasI=calificacion!!.estrellas
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
    fun observeDataTodas(){

        if (arguments!=null){
            val evento: Evento = arguments?.getSerializable("evento") as Evento
            viewModelTodas.fetchCalificacionesEventosData(evento.id).observe(viewLifecycleOwner, Observer {
                if (!it.isEmpty()){
                    for (item in it){
                        calificacionTotal+=item.estrellas
                    }
                    calificacionTotal=calificacionTotal/it.size
                    presenter.editCalificacionEventoGeneral(String.format("%.1f", calificacionTotal), evento.id)
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
        if (arguments!=null) {
            val evento: Evento = arguments?.getSerializable("evento") as Evento
            if (calificacion!=null){
                if (calificacion!!.estrellas!=estrellasI || calificacion!!.comentario!=comentario){
                    calificacion!!.estrellas=estrellasI
                    calificacion!!.comentario=comentario
                    presenter.editCalificacionEventoUser(calificacion!!)
                    observeDataTodas()
                }
            }else{
                var calificacion = CalificacionEvento()
                calificacion.comentario = comentario
                calificacion.estrellas = estrellasI
                calificacion.idEvento=evento.id
                presenter.addCalificacionEvento(calificacion)
                observeDataTodas()
            }
        }
    }

    override fun unaEstrella() {
        limpiar()
        estrellas.text="1/5"
        unaEstrella.setColorFilter(Color.parseColor(color2))
    }

    override fun dosEstrella() {
        limpiar()
        estrellas.text="2/5"
        unaEstrella.setColorFilter(Color.parseColor(color2))
        dosEstrella.setColorFilter(Color.parseColor(color2))
    }

    override fun tresEstrella() {
        limpiar()
        estrellas.text="3/5"
        unaEstrella.setColorFilter(Color.parseColor(color2))
        dosEstrella.setColorFilter(Color.parseColor(color2))
        tresEstrella.setColorFilter(Color.parseColor(color2))
    }

    override fun cuatroEstrella() {
        limpiar()
        estrellas.text="4/5"
        unaEstrella.setColorFilter(Color.parseColor(color2))
        dosEstrella.setColorFilter(Color.parseColor(color2))
        tresEstrella.setColorFilter(Color.parseColor(color2))
        cuatroEstrella.setColorFilter(Color.parseColor(color2))
    }

    override fun cincoEstrella() {
        limpiar()
        estrellas.text="5/5"
        unaEstrella.setColorFilter(Color.parseColor(color2))
        dosEstrella.setColorFilter(Color.parseColor(color2))
        tresEstrella.setColorFilter(Color.parseColor(color2))
        cuatroEstrella.setColorFilter(Color.parseColor(color2))
        cincoEstrella.setColorFilter(Color.parseColor(color2))
    }

    override fun showSuccess() {
        limpiar()
        unaEstrella()
        comentario.setText("")
        Toast.makeText(context,"Calificacion guardada correctamente", Toast.LENGTH_SHORT).show()

    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }



}