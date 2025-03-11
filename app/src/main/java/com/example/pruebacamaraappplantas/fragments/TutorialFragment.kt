package com.example.pruebacamaraappplantas.fragments

import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pruebacamaraappplantas.Adapters.TutorialNavigationListener
import com.example.pruebacamaraappplantas.databinding.FragmentTutorialBinding

class TutorialFragment : Fragment() {
    private var listener: TutorialNavigationListener? = null
    private lateinit var binding: FragmentTutorialBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TutorialNavigationListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialBinding.inflate(inflater, container, false)



        return binding.root
    }

    companion object {
        fun newInstance(title: String, desc: String, image: Int) = TutorialFragment().apply {
            arguments = Bundle().apply {
                putString("title", title)
                putString("desc", desc)
                putInt("image", image)
            }
        }
    }
}

