package com.example.pruebacamaraappplantas.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pruebacamaraappplantas.Activities.MainActivity
import com.example.pruebacamaraappplantas.Activities.TutorialActivity
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Cambiar al fragmento de login cuando se pulse el texto
        binding.textView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.tvlogin.setOnClickListener {
            val intent = Intent(requireContext(), TutorialActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}
