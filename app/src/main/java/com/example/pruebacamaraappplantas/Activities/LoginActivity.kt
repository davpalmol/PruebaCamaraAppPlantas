package com.example.pruebacamaraappplantas.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.databinding.ActivityLoginBinding
import com.example.pruebacamaraappplantas.fragments.LoginFragment
import com.example.pruebacamaraappplantas.fragments.RegisterFragment
import com.example.pruebacamaraappplantas.fragments.WelcomeFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar fragmento inicial (Welcome)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, WelcomeFragment())
            .commit()


    }
}

