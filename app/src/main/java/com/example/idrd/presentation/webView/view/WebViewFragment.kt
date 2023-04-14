package com.example.idrd.presentation.webView.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.idrd.R
import kotlinx.android.synthetic.main.fragment_web_view.view.*


class WebViewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater!!.inflate(R.layout.fragment_web_view, container, false)

        view.webView.loadUrl("https://app.powerbi.com/links/ktzdhpuFiP?ctid=07da67a0-1f43-4e8c-977f-5f88b6470ee6&pbi_source=linkShare")
        return view
    }
}