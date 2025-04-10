package com.example.pruebacamaraappplantas.Activities

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.databinding.ActivityLoginBinding
import com.example.pruebacamaraappplantas.fragments.LoginFragment
import com.example.pruebacamaraappplantas.fragments.RegisterFragment
import com.example.pruebacamaraappplantas.fragments.WelcomeFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()

        // Cargar fragmento inicial (Welcome)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, WelcomeFragment())
            .commit()


    }
}

