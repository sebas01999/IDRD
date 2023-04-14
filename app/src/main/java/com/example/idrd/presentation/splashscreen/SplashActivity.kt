package com.example.idrd.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.example.idrd.R
import com.example.idrd.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val animacion1=AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        val animacion2=AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)

        bytextView.animation=animacion2
        logoBottom.animation=animacion2
        logoImageView.animation=animacion1

        Handler(Looper.getMainLooper()).postDelayed({
            val intent =Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },4000)

    }
}