package com.example.idrd.presentation.cuenta_AdminParque.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.idrd.R
import com.example.idrd.base.BaseCod
import com.example.idrd.data.database.RepoUser
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.acceder_solicitudes.view.FragmentAccederSolicitudes
import com.example.idrd.presentation.configuración_cuenta.view.FragmentConfiguracionCuenta
import com.example.idrd.presentation.contacto.ContactoFragment
import com.example.idrd.presentation.crud_eventos.view.CrudEventosFragment
import com.example.idrd.presentation.notificaciones.model.NotificacionesViewModel
import com.example.idrd.presentation.notificaciones.view.NotificacionesFragment
import com.example.idrd.presentation.webView.view.WebViewFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_cuenta__admin_parque.*
import kotlinx.android.synthetic.main.fragment_cuenta__admin_parque.view.*
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception


class CuentaAdminParqueFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(NotificacionesViewModel::class.java) }
    var bitmap: Bitmap? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_cuenta__admin_parque, container, false)
        if (arguments!=null){
            val user: Users = arguments?.getSerializable("user") as Users
            view.nombreadminP.text=user.nombre
            view.correoadminP.text=user.correo
            view.direcadminP.text=user.direction
        }
        view.botonEventos.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = CrudEventosFragment()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.botonconfigadminP.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = FragmentConfiguracionCuenta()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.botonSolicitudesAdminP.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = FragmentAccederSolicitudes()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.botonnotificacionesadminP.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = NotificacionesFragment()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.ayudaadminP.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = ContactoFragment()
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }
        view.cerrarsesionAp.setOnClickListener {
            signOut()
        }
        view.botongenerarQR.setOnClickListener {
            val user: Users = arguments?.getSerializable("user") as Users
            try {
                var barcodeEncoder:BarcodeEncoder= BarcodeEncoder()
                bitmap=barcodeEncoder.encodeBitmap(
                    user.rol,
                    BarcodeFormat.QR_CODE,
                    750,
                    750
                )
                val filename = user.rol+".jpg"
                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/jpeg" // o el tipo de archivo correspondiente
                    putExtra(Intent.EXTRA_TITLE, filename) // nombre de tu archivo y extensión
                }
                startActivityForResult(intent, 1)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        view.botonreportesadminp.setOnClickListener {
            val transaction=fragmentManager?.beginTransaction()
            val fragmento = WebViewFragment()
            fragmento.arguments=arguments
            BaseCod().transactionFragmet(transaction!!,fragmento)
        }

        observeData()
        return view
    }
    private fun signOut() {
        activity?.let { RepoUser().signOut(it) }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                    try {
                        val stream: OutputStream? = requireActivity().contentResolver.openOutputStream(uri)
                        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, stream) // comprime la imagen y la guarda en el archivo
                        stream?.flush()
                        stream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
            }
        }
    }
    fun observeData(){
        val auth= FirebaseAuth.getInstance().currentUser?.uid.toString()
        viewModel.fetchNotificacionesData(auth).observe(viewLifecycleOwner, Observer {
            if (!it.isEmpty()){
                val numer =it.count { noti->
                    noti.visto==false
                }
                if (numer==0){
                    badgeAp.clear()
                }else{
                    badgeAp.setNumber(numer)
                }

            }
        })
    }
}