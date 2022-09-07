package com.example.idrd.presentation.editar_solicitud.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.idrd.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class fragment_editar_solicitud: BottomSheetDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_form, container, false)

        return view
    }
}