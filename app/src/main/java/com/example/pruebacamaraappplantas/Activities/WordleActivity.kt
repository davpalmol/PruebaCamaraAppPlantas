package com.example.pruebacamaraappplantas.Activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebacamaraappplantas.R
import kotlin.random.Random

class WordleActivity : AppCompatActivity() {
    private val wordsToGuess = listOf("PLANT", "WATER", "GROW", "SEED", "DIRT", "OLIVE", "PHOENIX")
    private val wordToGuess = wordsToGuess[Random.nextInt(wordsToGuess.size)] // Selección aleatoria
    private var currentRow = 0
    private var currentCol = 0
    private val maxRows = 6
    private lateinit var wordGrid: GridLayout
    private val editTexts = mutableListOf<EditText>()
    private var isDeleting = false
    private var isSubmitting = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordle)

        wordGrid = findViewById(R.id.wordGrid)
        setupGrid()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupGrid() {
        val wordLength = wordToGuess.length // Obtener la longitud de la palabra

        // Establecer el número de columnas del GridLayout según la longitud de la palabra
        wordGrid.columnCount = wordLength

        // Calcular el tamaño de las celdas basado en la longitud de la palabra
        val cellSize = resources.displayMetrics.widthPixels / wordLength - 40  // 40dp de márgenes (ajustable)

        for (i in 0 until maxRows) {
            for (j in 0 until wordLength) {
                val letterBox = EditText(this)
                // Asegúrate de usar GridLayout.LayoutParams aquí, no LinearLayout.LayoutParams
                val params = GridLayout.LayoutParams(
                    GridLayout.spec(i, GridLayout.FILL, 1f),
                    GridLayout.spec(j, GridLayout.FILL, 1f)
                ).apply {
                    width = cellSize
                    height = cellSize
                    setMargins(5, 5, 5, 5)
                }
                letterBox.layoutParams = params
                letterBox.textSize = 24f
                letterBox.setBackgroundColor(Color.LTGRAY)
                letterBox.gravity = Gravity.CENTER
                letterBox.isSingleLine = true
                letterBox.setPadding(10, 10, 10, 10)
                letterBox.isFocusable = false // Deshabilitar por defecto
                letterBox.isCursorVisible = false // Ocultar la barrita vertical del cursor
                letterBox.setBackgroundColor(Color.WHITE)
                letterBox.setTextColor(Color.BLACK)
                letterBox.setPadding(10, 10, 10, 10)
                letterBox.background = resources.getDrawable(R.drawable.edittext_border_gray, null)


                // Manejo de escritura automática
                letterBox.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (!isDeleting && !isSubmitting && s?.length == 1) {

                            letterBox.background = resources.getDrawable(R.drawable.edittext_border_black, null)

                            val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
                                letterBox,
                                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f),
                                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f)
                            )
                            scaleUp.duration = 100

                            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                                letterBox,
                                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
                            )
                            scaleDown.duration = 100

                            val animatorSet = AnimatorSet()
                            animatorSet.playSequentially(scaleUp, scaleDown)
                            animatorSet.start()

                            val upperCaseLetter = s.toString().uppercase()
                            letterBox.removeTextChangedListener(this) // Evita bucles infinitos
                            letterBox.setText(upperCaseLetter)
                            letterBox.setSelection(upperCaseLetter.length) // Mueve el cursor al final
                            letterBox.addTextChangedListener(this) // Vuelve a agregar el listener

                            if (currentCol < wordLength - 1) {
                                currentCol++
                                editTexts[currentRow * wordLength + currentCol].isFocusableInTouchMode = true
                                editTexts[currentRow * wordLength + currentCol].requestFocus()
                            } else {
                                isSubmitting = true
                                submitWord()
                            }
                        }
                    }
                    override fun afterTextChanged(s: Editable?) {}
                })

                // Manejo de la tecla borrar
                letterBox.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                        isDeleting = true
                        if (currentCol > 0) {
                            editTexts[currentRow * wordLength + currentCol].background = resources.getDrawable(R.drawable.edittext_border_gray, null)
                            editTexts[currentRow * wordLength + currentCol].text.clear()
                            editTexts[currentRow * wordLength + currentCol].isFocusable = false
                            currentCol--
                            editTexts[currentRow * wordLength + currentCol].background = resources.getDrawable(R.drawable.edittext_border_gray, null)
                            editTexts[currentRow * wordLength + currentCol].text.clear()
                            editTexts[currentRow * wordLength + currentCol].isFocusableInTouchMode = true
                            editTexts[currentRow * wordLength + currentCol].requestFocus()
                        } else {
                            editTexts[currentRow * wordLength].text.clear()
                        }
                        isDeleting = false
                        true
                    } else {
                        false
                    }
                }

                editTexts.add(letterBox)
                wordGrid.addView(letterBox)
            }
        }

        // Habilitar solo la primera celda
        editTexts[0].isFocusableInTouchMode = true
        editTexts[0].requestFocus()

    }




    private fun submitWord() {
        val wordLength = wordToGuess.length
        val guessedWord = StringBuilder()

        for (i in 0 until wordLength) {
            val letter = editTexts[currentRow * wordLength + i].text.toString().uppercase()
            if (letter.isEmpty()) return // Evita errores si la fila no está completa
            guessedWord.append(letter)
        }

        if (guessedWord.length == wordLength) {
            updateGrid(guessedWord.toString())
        } else {
            Toast.makeText(this, "La palabra debe tener $wordLength letras", Toast.LENGTH_SHORT).show()
        }
        isSubmitting = false
    }

    private fun updateGrid(guessedWord: String) {
        val wordLength = wordToGuess.length
        for (i in guessedWord.indices) {
            val index = currentRow * wordLength + i
            val letterBox = editTexts[index]
            val originalColor = letterBox.background

            val flipAnimation = ObjectAnimator.ofFloat(letterBox, View.ROTATION_Y, 0f, 90f)
            flipAnimation.duration = 250
            flipAnimation.interpolator = AccelerateDecelerateInterpolator()

            flipAnimation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    when {
                        guessedWord[i] == wordToGuess[i] -> letterBox.setBackgroundColor(Color.GREEN)
                        wordToGuess.contains(guessedWord[i]) -> letterBox.setBackgroundColor(Color.YELLOW)
                        else -> letterBox.setBackgroundColor(Color.GRAY)
                    }
                    val flipBack = ObjectAnimator.ofFloat(letterBox, View.ROTATION_Y, 90f, 0f)
                    flipBack.duration = 250
                    flipBack.interpolator = AccelerateDecelerateInterpolator()
                    flipBack.start()
                }
            })

            Handler(Looper.getMainLooper()).postDelayed({
                flipAnimation.start()
            }, (i * 10).toLong()) // Retraso progresivo para cada letra
        }


        if (guessedWord == wordToGuess) {
            Toast.makeText(this, "¡Ganaste!", Toast.LENGTH_SHORT).show()
        } else if (currentRow < maxRows - 1) {
            currentRow++
            currentCol = 0
            editTexts[currentRow * wordLength].isFocusableInTouchMode = true
            editTexts[currentRow * wordLength].requestFocus()
        } else {
            Toast.makeText(this, "¡Perdiste! La palabra era $wordToGuess", Toast.LENGTH_SHORT).show()
        }
    }
}
