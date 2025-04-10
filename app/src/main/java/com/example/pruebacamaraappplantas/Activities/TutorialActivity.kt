package com.example.pruebacamaraappplantas.Activities

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pruebacamaraappplantas.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TutorialActivity : AppCompatActivity() {
    private var stepIndex = 0
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var fab: FloatingActionButton

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

        // Desactivar el modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Establecer la vista del tutorial
        setContentView(R.layout.activity_tutorial)

        hideSystemUI()

        // Inicializar vistas
        bottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomAppBar = findViewById(R.id.bottomAppBar)

        bottomAppBar.menu.setGroupEnabled(0, false)

        fab = findViewById(R.id.fabCaptureImage)

        findViewById<View>(R.id.btnSaltarTutorial).setOnClickListener { endTutorial() }
        findViewById<View>(R.id.halfRightView).setOnClickListener { nextStep() }
        findViewById<View>(R.id.halfLeftView).setOnClickListener { previousStep() }

        // Iniciar tutorial
        showStep()
    }

    private fun showStep() {
        when (stepIndex) {
            0 -> {
                findViewById<View>(R.id.view1).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto1).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto2).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto3).visibility = View.INVISIBLE
                findViewById<Button>(R.id.btnSaltarTutorial).visibility = View.VISIBLE
                findViewById<View>(R.id.ranking).setBackgroundColor(Color.parseColor("#99000000"))
            }

            1 -> {
                findViewById<View>(R.id.view1).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto1).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto3).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto2).visibility = View.INVISIBLE
                findViewById<View>(R.id.ranking).setBackgroundColor(Color.WHITE)
                findViewById<Button>(R.id.btnSaltarTutorial).visibility = View.INVISIBLE
                findViewById<View>(R.id.calendarView).setBackgroundColor(Color.parseColor("#70000000"))
            }

            2 -> {
                findViewById<View>(R.id.imagentuto1).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto3).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto2).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto4).visibility = View.INVISIBLE
                findViewById<View>(R.id.ranking).setBackgroundColor(Color.parseColor("#99000000"))
                findViewById<View>(R.id.calendarView).setBackgroundColor(Color.WHITE)
            }

            3 -> {
                findViewById<View>(R.id.imagentuto2).visibility = View.INVISIBLE
                findViewById<View>(R.id.calendarView).setBackgroundColor(Color.parseColor("#70000000"))
                findViewById<View>(R.id.imagentuto4).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto5).visibility = View.INVISIBLE
                updateBottomBarSelection(R.id.nav_home)
            }

            4 -> {
                findViewById<View>(R.id.imagentuto4).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto5).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto6).visibility = View.INVISIBLE
                updateBottomBarSelection(R.id.nav_rankings)
            }

            5 -> {
                findViewById<View>(R.id.imagentuto5).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto6).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto7).visibility = View.INVISIBLE
                updateBottomBarSelection(R.id.nav_library)
            }

            6 -> {
                findViewById<View>(R.id.imagentuto6).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto7).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto8).visibility = View.INVISIBLE
                updateBottomBarSelection(R.id.nav_settings)
            }

            7 -> {
                findViewById<View>(R.id.imagentuto7).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto8).visibility = View.VISIBLE
                updateBottomBarSelection(null)
                animateFab(fab)
            }
        }
    }

    private fun updateBottomBarSelection(itemId: Int?) {
        bottomNavigationView.post {
            val menuItem = itemId?.let { bottomNavigationView.menu.findItem(it) }
            if (menuItem != null) {
                menuItem.setChecked(true)
            } else {
                bottomNavigationView.menu.findItem(R.id.nav_settings).setChecked(false)
            }
        }
    }

    private fun nextStep() {
        stepIndex++
        if (stepIndex > 7) {
            endTutorial()
        } else {
            showStep()
        }
    }

    private fun previousStep() {
        if (stepIndex > 0) {
            stepIndex--
            showStep()
        }
    }

    private fun endTutorial() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun animateFab(fab: FloatingActionButton) {
        val animator = ValueAnimator.ofFloat(1f, 1.3f, 1f) // Tamaño normal → grande → normal
        animator.duration = 1000 // 1 segundo por ciclo
        animator.repeatCount = ValueAnimator.INFINITE // Animación infinita
        animator.interpolator = LinearInterpolator() // Transición suave
        animator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            fab.scaleX = scale
            fab.scaleY = scale
        }
        animator.start()
    }
}