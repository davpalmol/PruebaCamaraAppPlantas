package com.example.pruebacamaraappplantas.Activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebacamaraappplantas.Adapters.TutorialNavigationListener
import com.example.pruebacamaraappplantas.Adapters.TutorialPagerAdapter
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.TutorialOverlay
import com.example.pruebacamaraappplantas.databinding.ActivityTutorialBinding
import com.example.pruebacamaraappplantas.fragments.TutorialFragment

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val tutorial = TutorialOverlay(this)
        tutorial.startTutorial()
    }
}
