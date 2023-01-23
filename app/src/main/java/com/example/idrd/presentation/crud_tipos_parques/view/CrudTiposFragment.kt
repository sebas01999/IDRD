package com.example.idrd.presentation.crud_tipos_parques.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.idrd.R
import com.example.idrd.data.model.TiposParque
import com.example.idrd.domain.interactor.crud_TiposParques.CrudTiposParquesInteractorImpl
import com.example.idrd.presentation.agregarTiposParques.view.agregar_tipos_parqueFragment
import com.example.idrd.presentation.crud_tipos_parques.CrudTiposParquesContract
import com.example.idrd.presentation.crud_tipos_parques.crudTiposParquesPresenter.CrudTiposParquesPresenter
import com.example.idrd.presentation.inicio.model.TiposViewModel

import kotlinx.android.synthetic.main.fragment_crud_tipos.*
import kotlinx.android.synthetic.main.fragment_crud_tipos.view.*


class CrudTiposFragment : Fragment(),TiposAdapter.OnItemClickListener,CrudTiposParquesContract.CrudTiposView {

    private var tipo:TiposParque?= null
    lateinit var presenter:CrudTiposParquesPresenter
    private val viewModel by lazy{ ViewModelProvider(this).get(TiposViewModel::class.java)}
    private lateinit var adapter: TiposAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_crud_tipos, container, false)
        presenter= CrudTiposParquesPresenter(CrudTiposParquesInteractorImpl())

        presenter.attachView(this)

        adapter= context?.let {
            TiposAdapter(it,this) }!!

        view.rv_tipos_parque.layoutManager= LinearLayoutManager(context)

        view.rv_tipos_parque.adapter= adapter

        view.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_agregar->{
                    val fragment=agregar_tipos_parqueFragment()
                    fragment.show(childFragmentManager,"SimpleDialog")
                    true
                }
                R.id.menu_modificar->{
                    editar()
                    true

                }
                R.id.menu_eliminar->{
                    borrar()
                    true

                }
                else->false
            }
        }

        view.cancelar_tipo_parque.setOnClickListener {
            tipo=null
            cancelar()
        }
        observerData()

        return view
    }

    override fun showError(msgError: String?) {
        Toast.makeText(context,msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        cancelar_tipo_parque.visibility=View.GONE
        progressBar_guardar_tipo_parque.visibility=View.VISIBLE

    }

    override fun hideProgressDialog() {
        cancelar_tipo_parque.visibility=View.VISIBLE
        progressBar_guardar_tipo_parque.visibility=View.GONE
    }

    override fun editar() {
        if (tipo!=null){
            val nombre:String = txt_tipo_parque.editText?.text.toString().trim()

            if (presenter.checkEmptyTipo(nombre)){
                txt_tipo_parque.error="Ingrese un tipo de parque"
                return
            }
            tipo?.tipo=nombre
            presenter.editar(tipo!!)
            adapter.notifyDataSetChanged()
            tipo=null
        }

    }

    override fun borrar() {
        if(tipo!=null){
            presenter.borrar(tipo!!)
            observerData()
            tipo=null
        }
    }

    override fun cancelar() {
       form_agregar_tipo.visibility=View.GONE
        tipo=null
    }

    override fun showSuccess(msgSuccess: String?) {
        tipo=null
        Toast.makeText(context,msgSuccess, Toast.LENGTH_SHORT).show()
        form_agregar_tipo.visibility=View.GONE
    }

    fun observerData(){
        viewModel.fetchTiposParqueData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(item: TiposParque) {
        tipo= item
        tipo_parque.setText(tipo!!.tipo)
        form_agregar_tipo.visibility= View.VISIBLE

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

}