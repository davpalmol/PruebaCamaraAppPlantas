package com.example.pruebacamaraappplantas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.tvlogin.setOnClickListener {
            val loginFragment = LoginFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, loginFragment)
                .addToBackStack(null) // Para permitir regresar al fragmento anterior
                .commit()
        }

        binding.tvRegister.setOnClickListener {
            val registerFragment = RegisterFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, registerFragment)
                .addToBackStack(null) // Para permitir regresar al fragmento anterior
                .commit()
        }

        return binding.root
    }
}
