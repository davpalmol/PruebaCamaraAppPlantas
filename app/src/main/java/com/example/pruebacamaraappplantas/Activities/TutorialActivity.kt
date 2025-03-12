package com.example.pruebacamaraappplantas.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pruebacamaraappplantas.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class TutorialActivity : AppCompatActivity() {
    private var overlayView: View? = null
    private var stepIndex = 0
    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Desactivar el modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        // Iniciar el tutorial
        startTutorial()
    }

    private fun startTutorial() {
        showOverlay()
        showStep()
    }

    private fun showOverlay() {
        val rootView = window.decorView.rootView as ViewGroup
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_tutorial, rootView, false)
        rootView.addView(overlayView)

        bottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomAppBar = findViewById(R.id.bottomAppBar)

        bottomAppBar.menu.setGroupEnabled(0, false)

        findViewById<View>(R.id.btnSaltarTutorial)
            .setOnClickListener { v: View? -> endTutorial() }
        findViewById<View>(R.id.halfRightView)
            .setOnClickListener { v: View? -> nextStep() }
        findViewById<View>(R.id.halfLeftView)
            .setOnClickListener { v: View? -> previousStep() }
    }

    private fun showStep() {
        when (stepIndex) {
            0 -> {
                findViewById<View>(R.id.view1).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto1).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto2).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto3).visibility = View.INVISIBLE
                findViewById<View>(R.id.ranking).setBackgroundColor(Color.parseColor("#99000000"))
            }

            1 -> {
                findViewById<View>(R.id.view1).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto1).visibility = View.INVISIBLE
                findViewById<View>(R.id.imagentuto3).visibility = View.VISIBLE
                findViewById<View>(R.id.imagentuto2).visibility = View.INVISIBLE
                findViewById<View>(R.id.ranking).setBackgroundColor(Color.WHITE)
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
                updateBottomBarSelection(R.id.nav_settings)
            }
        }
    }

    private fun updateBottomBarSelection(itemId: Int) {
        if (bottomNavigationView != null) {
            bottomNavigationView!!.post {
                val menuItem = bottomNavigationView!!.menu.findItem(itemId)
                menuItem?.setChecked(true)
                    ?: Log.d("TutorialActivity", "Menu item not found: $itemId")
            }
        }
    }

    private fun nextStep() {
        stepIndex++
        if (stepIndex > 6) {
            endTutorial()
        } else {
            showStep()
        }
    }

    private fun previousStep() {
        stepIndex--
        if (stepIndex >= 0) {
            showStep()
        }
    }

    private fun endTutorial() {
        val rootView = window.decorView.rootView as ViewGroup
        rootView.removeView(overlayView)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}