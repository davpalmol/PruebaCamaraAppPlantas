package com.example.pruebacamaraappplantas.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Asegúrate de agregar la librería Glide para cargar imágenes
import com.example.pruebacamaraappplantas.Api.PlantaPrueba
import com.example.pruebacamaraappplantas.R

class PlantAdapter(private val plantList: MutableList<PlantaPrueba>) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    // Crea las vistas para cada ítem del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    // Vincula los datos a las vistas para cada ítem
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        
        // Cargar la imagen usando Glide
        Glide.with(holder.itemView.context)
            .load(plant.imageUri) // Usamos una URL para la imagen
            .into(holder.plantImageView)

        // Setear los nombres de la planta
        holder.plantNameTextView.text = plant.nombre
        holder.plantScientificNameTextView.text = "(${plant.nombreCientifico})"
    }

    // Retorna el tamaño de la lista
    override fun getItemCount(): Int {
        return plantList.size
    }

    // ViewHolder que mantiene las vistas para cada ítem
    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImageView: ImageView = itemView.findViewById(R.id.miImagen)
        val plantNameTextView: TextView = itemView.findViewById(R.id.plantNameTextView)
        val plantScientificNameTextView: TextView = itemView.findViewById(R.id.plantScientificNameTextView)
    }
}
