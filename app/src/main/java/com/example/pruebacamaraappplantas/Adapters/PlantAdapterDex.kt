package com.example.pruebacamaraappplantas.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Asegúrate de agregar la librería Glide para cargar imágenes
import com.example.pruebacamaraappplantas.Api.PlantaPrueba
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.entity.Plant

class PlantAdapterDex(private val plants: List<Plant>) : RecyclerView.Adapter<PlantAdapterDex.PlantViewHolderDex>() {

    class PlantViewHolderDex(view: View) : RecyclerView.ViewHolder(view) {
        val plantName: TextView = view.findViewById(R.id.plantName)
        val plantImage: ImageView = view.findViewById(R.id.plantImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolderDex {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant_dex, parent, false)
        return PlantViewHolderDex(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolderDex, position: Int) {
        val plant = plants[position]
        holder.plantName.text = plant.name
        holder.plantImage.setImageResource(plant.imageResId)
    }

    override fun getItemCount() = plants.size
}

