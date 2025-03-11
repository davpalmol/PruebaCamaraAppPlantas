package com.example.pruebacamaraappplantas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        // Cambiar al fragmento de login cuando se pulse el texto
        binding.textView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
}
