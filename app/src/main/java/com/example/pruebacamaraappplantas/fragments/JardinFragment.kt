package com.example.pruebacamaraappplantas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebacamaraappplantas.Adapters.PlantAdapter
import com.example.pruebacamaraappplantas.Api.PlantaPrueba
import com.example.pruebacamaraappplantas.R

class JardinFragment(private val plantList: MutableList<PlantaPrueba>) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var plantAdapter: PlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_jardin, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewPlants)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        plantAdapter = PlantAdapter(plantList)
        recyclerView.adapter = plantAdapter

        return view
    }
}
