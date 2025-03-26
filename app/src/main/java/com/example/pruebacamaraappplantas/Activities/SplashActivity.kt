package com.example.pruebacamaraappplantas.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebacamaraappplantas.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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