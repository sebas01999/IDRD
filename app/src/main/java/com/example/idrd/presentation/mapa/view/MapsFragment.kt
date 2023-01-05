package com.example.idrd.presentation.mapa.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.idrd.R
import com.example.idrd.data.model.Parque
import com.example.idrd.presentation.info_mapa.view.Fragment_infomapa

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(),GoogleMap.OnMarkerClickListener {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        if(arguments!=null){
            val parque: Parque = arguments?.getSerializable("parque") as Parque


            val ubicacion = LatLng(parque.locali.latitude, parque.locali.longitude)

            googleMap.addMarker(MarkerOptions().position(ubicacion).title(parque.nombre))
            val cameraPosition = CameraPosition.builder().target(ubicacion).zoom(15F).build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            googleMap.setOnMarkerClickListener(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val info= Fragment_infomapa()
        info.arguments=arguments
        info.show(childFragmentManager,"SimpleDialog")
        return false
    }
}

