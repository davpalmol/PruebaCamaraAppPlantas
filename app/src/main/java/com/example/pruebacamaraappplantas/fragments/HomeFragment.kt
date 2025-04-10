package com.example.pruebacamaraappplantas.fragments

import com.example.pruebacamaraappplantas.Activities.WordleActivity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebacamaraappplantas.Activities.PlantDexActivity
import com.example.pruebacamaraappplantas.Adapters.PlantAdapter
import com.example.pruebacamaraappplantas.Api.PlantaPrueba
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var plantAdapter: PlantAdapter
    private val plantListPrueba = mutableListOf<PlantaPrueba>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        for (int in 1..4) {
            val plant = PlantaPrueba("Planta $int", 0.5, "Planta $int", R.drawable.blanca)
            plantListPrueba.add(plant)
        }

        binding.cardWordle.setOnClickListener {
            val intent = Intent(requireContext(), WordleActivity::class.java)
            startActivity(intent)
        }

        binding.cardPlantdex.setOnClickListener {
            val intent = Intent(requireContext(), PlantDexActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.plantsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        plantAdapter = PlantAdapter(plantListPrueba)
        recyclerView.adapter = plantAdapter
    }
}
