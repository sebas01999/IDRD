package com.example.idrd.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.idrd.R
import com.example.idrd.base.BaseActivity
import com.example.idrd.presentation.login.view.LoginFragment
import com.example.idrd.presentation.main.adapters.viewAdapter
import com.example.idrd.presentation.register.view.RegisterFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpTabs()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    private fun setUpTabs(){

        val adapter = viewAdapter(supportFragmentManager)
        adapter.addFragmentList(LoginFragment(), "Ingresa")
        adapter.addFragmentList(RegisterFragment(), "Registrate")

        viewpager.adapter= adapter
        tabs.setupWithViewPager(viewpager)


    }
}