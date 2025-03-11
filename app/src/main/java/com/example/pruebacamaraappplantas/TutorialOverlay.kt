package com.example.pruebacamaraappplantas

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.pruebacamaraappplantas.Activities.MainActivity

class TutorialOverlay(private val activity: Activity) {
    private var overlayView: View? = null
    private var stepIndex = 0

    fun startTutorial() {
        showOverlay()
        showStep()
    }

    private fun showOverlay() {
        val rootView = activity.window.decorView.rootView as ViewGroup
        overlayView = LayoutInflater.from(activity).inflate(R.layout.overlay_tutorial, rootView, false)
        rootView.addView(overlayView)

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

                val imagentuto2 = activity.findViewById<View>(R.id.imagentuto2)
                imagentuto2?.visibility = View.VISIBLE

                val imagentuto3 = activity.findViewById<View>(R.id.imagentuto3)
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

                val imagentuto2 = activity.findViewById<View>(R.id.imagentuto2)
                imagentuto2?.visibility = View.INVISIBLE

                val imagentuto3 = activity.findViewById<View>(R.id.imagentuto3)
                imagentuto3?.visibility = View.VISIBLE
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
        }
    }

    private fun nextStep() {
        stepIndex++
        if (stepIndex > 2) {
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
