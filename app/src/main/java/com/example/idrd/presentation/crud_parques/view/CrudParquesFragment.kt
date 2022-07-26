package com.example.idrd.presentation.crud_parques.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.domain.interactor.crudParques.CrudParqueInteractorImpl
import com.example.idrd.presentation.agregarParque.view.agregarParqueFragment
import com.example.idrd.presentation.crud_parques.CrudParqueContract
import com.example.idrd.presentation.crud_parques.CrudParquesPresenter.CrudParquesPresenter
import com.example.idrd.presentation.inicio.model.MainViewModel
import com.example.idrd.presentation.inicio.view.MainAdapter
import kotlinx.android.synthetic.main.fragment_agregar_parque.*
import kotlinx.android.synthetic.main.fragment_crud_parques.*
import kotlinx.android.synthetic.main.fragment_crud_parques.cancelar
import kotlinx.android.synthetic.main.fragment_crud_parques.descripcion
import kotlinx.android.synthetic.main.fragment_crud_parques.drop_items
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_descripcion
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_horario
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_nombre
import kotlinx.android.synthetic.main.fragment_crud_parques.etxt_ubicacion
import kotlinx.android.synthetic.main.fragment_crud_parques.horario
import kotlinx.android.synthetic.main.fragment_crud_parques.mostrarParque
import kotlinx.android.synthetic.main.fragment_crud_parques.nombre
import kotlinx.android.synthetic.main.fragment_crud_parques.ubicacion
import kotlinx.android.synthetic.main.fragment_crud_parques.view.*



class CrudParquesFragment : Fragment(), MainAdapter.OnItemClickListener, CrudParqueContract.CrudView {
    private lateinit var adapter: MainAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var parque:Parque?= null
    var filepath : Uri?=null
    lateinit var presenter: CrudParquesPresenter
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
        view.cancelar.setOnClickListener {
            parque=null
            cancelarV()
        }
        view.agregar_foto.setOnClickListener {
            addFoto()
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
                R.id.menu_eliminar -> {
                    borrar()
                    true
                }
                else -> false
            }
        }
        return view
    }

    override fun onItemClick(item: Parque) {
        parque=item

        nombre.setText(parque!!.nombre)
        ubicacion.setText(parque!!.ubicacion)
        horario.setText(parque!!.horario)
        descripcion.setText(parque!!.descripcion)
        mostrarParque.visibility= View.VISIBLE
    }

    fun observeData(){
        //shimmer_view_container.startShimmer()
        viewModel.fetchParqueData().observe(viewLifecycleOwner , Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility=View.GONE

            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })

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
        if(parque!=null){
            val nombre:String= etxt_nombre.editText?.text.toString().trim()
            val ubicacion:String= etxt_ubicacion.editText?.text.toString().trim()
            val horario:String= etxt_horario.editText?.text.toString().trim()
            val descripcion:String= etxt_descripcion.editText?.text.toString().trim()
            val tipo:String= drop_items.text.toString()

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

            parque?.nombre=nombre
            parque?.tipo=tipo
            parque?.ubicacion=ubicacion
            parque?.horario=horario
            parque?.descripcion=descripcion



            if (filepath==null){
                presenter.editar(parque!!)
            }else{
                presenter.editarFoto(parque!!,filepath!!)
            }
            observeData()
            parque=null
        }



    }

    override fun borrar() {
        if (parque!=null){
            presenter.borrar(parque!!)
            observeData()
            parque=null
        }
    }

    override fun cancelarV() {
        mostrarParque.visibility= View.GONE
        parque=null
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
        horario.setText("")
        descripcion.setText("")
        parque=null
        Toast.makeText(context,msgSuccess, Toast.LENGTH_SHORT).show()
        mostrarParque.visibility= View.GONE

    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }
}