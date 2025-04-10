package com.example.pruebacamaraappplantas.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebacamaraappplantas.R


class SplashActivity : AppCompatActivity() {

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN  // Oculta la barra de estado
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  // Oculta la barra de navegación
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Permite recuperar la barra con un gesto
                )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        hideSystemUI()

        // Transición entre actividades
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        // Después de unos segundos, iniciar la actividad principal
        Handler().postDelayed(Runnable {
            val intent = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }, 2000) // 3 segundos
    }


}