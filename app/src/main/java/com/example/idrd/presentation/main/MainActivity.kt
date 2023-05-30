package com.example.idrd.presentation.main

import android.content.Intent
import android.os.Bundle
import com.example.idrd.R
import com.example.idrd.base.BaseActivity
import com.example.idrd.presentation.login.view.LoginFragment
import com.example.idrd.presentation.main.adapters.ViewAdapter
import com.example.idrd.presentation.mainprincipal.view.Mainprincipal
import com.example.idrd.presentation.register.view.RegisterFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setUpTabs()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startMainActivity()
        }
    }
    private fun startMainActivity() {
        val intent = Intent(this, Mainprincipal::class.java)
        startActivity(intent)
        finish()
    }
    private fun setUpTabs(){

        val adapter = ViewAdapter(supportFragmentManager)
        adapter.addFragmentList(LoginFragment(), "Ingresa")
        adapter.addFragmentList(RegisterFragment(), "Registrate")

        viewpager.adapter= adapter
        tabs.setupWithViewPager(viewpager)


    }
}