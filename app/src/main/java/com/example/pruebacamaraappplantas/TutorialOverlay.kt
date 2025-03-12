package com.example.pruebacamaraappplantas

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.pruebacamaraappplantas.Activities.MainActivity
import com.google.android.material.bottomappbar.BottomAppBar

class TutorialOverlay(private val activity: Activity) {
    private var overlayView: View? = null
    private var stepIndex = 0
    private lateinit var bottomAppBar: BottomAppBar

    fun startTutorial() {
        showOverlay()
        showStep()
    }

    private fun showOverlay() {
        val rootView = activity.window.decorView.rootView as ViewGroup
        overlayView = LayoutInflater.from(activity).inflate(R.layout.overlay_tutorial, rootView, false)
        rootView.addView(overlayView)

        overlayView?.let { view ->
            bottomAppBar = view.findViewById(R.id.bottomAppBar)
        }
        overlayView?.findViewById<Button>(R.id.btnSaltarTutorial)?.setOnClickListener {
            endTutorial()
        }

        overlayView?.findViewById<View>(R.id.halfRightView)?.setOnClickListener {
            nextStep()
        }

        overlayView?.findViewById<View>(R.id.halfLeftView)?.setOnClickListener {
            previousStep()
        }
    }

    private fun showStep() {
        when (stepIndex) {
            0 -> {
                // Paso 1: Mostrar view1
                val view1 = activity.findViewById<View>(R.id.view1)
                view1?.visibility = View.VISIBLE

                val imagentuto1 = activity.findViewById<View>(R.id.imagentuto1)
                imagentuto1?.visibility = View.VISIBLE

                val imagentuto2 = activity.findViewById<View>(R.id.imagentuto2)
                imagentuto2?.visibility = View.INVISIBLE

                val imagentuto3 = activity.findViewById<View>(R.id.imagentuto3)
                imagentuto3?.visibility = View.INVISIBLE

                val ranking = activity.findViewById<View>(R.id.ranking)
                ranking?.let {
                    it.setBackgroundColor(Color.parseColor("#99000000")) // Fondo oscuro para el ranking
                }

            }
            1 -> {
                // Paso 2: Ocultar view1 y cambiar el color del ranking a blanco
                val view1 = activity.findViewById<View>(R.id.view1)
                view1?.visibility = View.INVISIBLE

                val imagentuto1 = activity.findViewById<View>(R.id.imagentuto1)
                imagentuto1?.visibility = View.INVISIBLE

                val imagentuto2 = activity.findViewById<View>(R.id.imagentuto3)
                imagentuto2?.visibility = View.VISIBLE

                val imagentuto3 = activity.findViewById<View>(R.id.imagentuto2)
                imagentuto3?.visibility = View.INVISIBLE


                val ranking = activity.findViewById<View>(R.id.ranking)
                ranking?.let {
                    it.setBackgroundColor(Color.WHITE) // Cambiar fondo del ranking a blanco
                }

                val calendar = activity.findViewById<View>(R.id.calendarView)
                calendar?.let {
                    it.setBackgroundColor(Color.parseColor("#70000000")) // Fondo aún más oscuro para el calendario
                }
            }
            2 -> {

                val imagentuto1 = activity.findViewById<View>(R.id.imagentuto1)
                imagentuto1?.visibility = View.INVISIBLE

                val imagentuto2 = activity.findViewById<View>(R.id.imagentuto3)
                imagentuto2?.visibility = View.INVISIBLE

                val imagentuto3 = activity.findViewById<View>(R.id.imagentuto2)
                imagentuto3?.visibility = View.VISIBLE

                val imagentuto4 = activity.findViewById<View>(R.id.imagentuto4)
                imagentuto4?.visibility = View.INVISIBLE

                // Paso 3: Cambiar el fondo del ranking a rojo oscuro y el calendario a un color más oscuro
                val ranking = activity.findViewById<View>(R.id.ranking)
                ranking?.let {
                    it.setBackgroundColor(Color.parseColor("#99000000")) // Fondo oscuro para el ranking
                }

                val calendar = activity.findViewById<View>(R.id.calendarView)
                calendar?.let {
                    it.setBackgroundColor(Color.WHITE) // Cambiar fondo del ranking a blanco
                }
            }

            3 -> {

                val imagentuto3 = activity.findViewById<View>(R.id.imagentuto2)
                imagentuto3?.visibility = View.INVISIBLE

                val calendar = activity.findViewById<View>(R.id.calendarView)
                calendar?.let {
                    it.setBackgroundColor(Color.parseColor("#70000000")) // Fondo oscuro para el ranking
                }

                val imagentuto4 = activity.findViewById<View>(R.id.imagentuto4)
                imagentuto4?.visibility = View.VISIBLE

                val imagentuto5 = activity.findViewById<View>(R.id.imagentuto5)
                imagentuto5?.visibility = View.INVISIBLE

                bottomAppBar?.post {
                    val menuItem = bottomAppBar.menu.findItem(R.id.nav_library)
                    if (menuItem != null) {
                        Log.d("TutorialOverlay", "Menu item found: $menuItem")
                        menuItem.isChecked = true
                    } else {
                        Log.d("TutorialOverlay", "Menu item not found")
                    }
                }

            }

            4 -> {

                val imagentuto4 = activity.findViewById<View>(R.id.imagentuto4)
                imagentuto4?.visibility = View.INVISIBLE

                val imagentuto5 = activity.findViewById<View>(R.id.imagentuto5)
                imagentuto5?.visibility = View.VISIBLE

                val imagentuto6 = activity.findViewById<View>(R.id.imagentuto6)
                imagentuto6?.visibility = View.INVISIBLE

                bottomAppBar?.post {
                    val menuItem = bottomAppBar.menu.findItem(R.id.nav_rankings)
                    if (menuItem != null) {
                        Log.d("TutorialOverlay", "Menu item found: $menuItem")
                        menuItem.isChecked = true
                    } else {
                        Log.d("TutorialOverlay", "Menu item not found")
                    }
                }


            }

            5 -> {

                val imagentuto5 = activity.findViewById<View>(R.id.imagentuto5)
                imagentuto5?.visibility = View.INVISIBLE

                val imagentuto6 = activity.findViewById<View>(R.id.imagentuto6)
                imagentuto6?.visibility = View.VISIBLE

                val imagentuto7 = activity.findViewById<View>(R.id.imagentuto7)
                imagentuto7?.visibility = View.INVISIBLE
            }

            6 -> {

                val imagentuto6 = activity.findViewById<View>(R.id.imagentuto6)
                imagentuto6?.visibility = View.INVISIBLE

                val imagentuto7 = activity.findViewById<View>(R.id.imagentuto7)
                imagentuto7?.visibility = View.VISIBLE

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
        // Eliminar el overlay
        val rootView = activity.window.decorView.rootView as ViewGroup
        overlayView?.let { rootView.removeView(it) }

        // Intent para abrir MainActivity
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)

        // Cerrar la actividad de tutorial
        activity.finish()
    }
}
