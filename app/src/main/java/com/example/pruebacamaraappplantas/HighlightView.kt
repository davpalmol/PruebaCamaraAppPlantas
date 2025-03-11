package com.example.pruebacamaraappplantas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class HighlightView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // Paint para la capa oscura
    private val overlayPaint = Paint().apply {
        color = Color.parseColor("#FFFFFF") // Oscurecido
        style = Paint.Style.FILL
    }

    // Paint para hacer la sección resaltada transparente
    private val transparentPaint = Paint().apply {
        color = Color.parseColor("#FFFFFF") // Oscurecido
        style = Paint.Style.FILL
    }

    private var highlightRect: RectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Guardamos la capa actual del canvas para pintar sobre ella
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        // Dibuja el fondo oscuro sobre toda la vista
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)

        // Dibuja la sección resaltada (transparente)
        canvas.drawRoundRect(highlightRect, 30f, 30f, transparentPaint)

        // Restauramos el canvas al estado original
        canvas.restoreToCount(layerId)
    }

    // Método para cambiar la posición y el tamaño del área resaltada
    fun setHighlightArea(x: Float, y: Float, width: Float, height: Float) {
        highlightRect.set(x, y, x + width, y + height)
        invalidate() // Redibuja la vista
    }
}
