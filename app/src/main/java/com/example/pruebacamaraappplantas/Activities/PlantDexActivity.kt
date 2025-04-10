package com.example.pruebacamaraappplantas.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebacamaraappplantas.Adapters.PlantAdapterDex
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.entity.Plant
import android.view.ScaleGestureDetector
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.view.View

class PlantDexActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlantAdapterDex
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var spanCount = 4
    private lateinit var columnSeekBar: SeekBar
    private val handler = Handler(Looper.getMainLooper())
    private val hideSeekBarRunnable = Runnable {
        columnSeekBar.visibility = View.GONE
    }


    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_dex)

        recyclerView = findViewById(R.id.plantsdexRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)

        val nombresPlantas = listOf(
            "Abedul", "Abeto", "Acebo", "Achicoria",
            "Adelfa", "Ajedrea", "Aladierno", "Albahaca", "Albahaca morada",
            "Alcornoque", "Almendro", "Almez", "Amapola", "Avellano",
            "Belladona", "Brezo", "Caléndula", "Cantueso", "Cardo borriquero",
            "Castañuela", "Castaño", "Cedro", "Chopo", "Ciprés",
            "Cornicabra", "Crisantemo", "Digital", "Diente de león", "Durillo",
            "Encina", "Enebro", "Escaramujo", "Espliego", "Espino blanco",
            "Flor de azahar", "Fresno", "Geranio", "Girasol", "Granado",
            "Hierbabuena", "Higuera", "Hinojo", "Hortensia",
            "Jacinto silvestre", "Jara", "Laurel", "Lavanda",
            "Limonero", "Margarita amarilla", "Manzanilla"
        )

        // Carga tus plantas (puedes extender esto a las 100)
        val plants = List(50) { index ->
            val nombre = nombresPlantas[index]
            val imageName = "plantdeximg_${index + 1}"
            val resId = resources.getIdentifier(imageName, "drawable", packageName)
            Plant(name = nombre, imageResId = resId)
        }

        adapter = PlantAdapterDex(plants)
        recyclerView.adapter = adapter

        recyclerView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            false // permite que el RecyclerView siga funcionando normalmente (scroll, etc)
        }

        columnSeekBar = findViewById(R.id.columnSeekBar)
        columnSeekBar.max = 3 // 0 = 1 columna, 3 = 4 columnas
        columnSeekBar.progress = spanCount - 1

        columnSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                spanCount = progress + 1
                recyclerView.layoutManager = GridLayoutManager(this@PlantDexActivity, spanCount)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Ocultar el SeekBar después de 3 segundos
                handler.removeCallbacks(hideSeekBarRunnable)
                handler.postDelayed(hideSeekBarRunnable, 3000)
            }
        })


        // Gesture detector para zoom
        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val scaleFactor = detector.scaleFactor

                if (scaleFactor < 0.95 && spanCount < 4) {
                    spanCount++
                } else if (scaleFactor > 1.05 && spanCount > 1) {
                    spanCount--
                }

                recyclerView.layoutManager = GridLayoutManager(this@PlantDexActivity, spanCount)

                // Mostrar SeekBar y sincronizar con spanCount
                columnSeekBar.visibility = View.VISIBLE
                columnSeekBar.progress = spanCount - 1

                // Reinicia el temporizador de desaparición
                handler.removeCallbacks(hideSeekBarRunnable)
                handler.postDelayed(hideSeekBarRunnable, 3000)

                return true
            }
        })
    }

}