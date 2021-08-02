package com.example.trainingproject.screens.cards

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trainingproject.databinding.FragmentPointScreenBinding

class PointFragment : Fragment() {
    private var _binding : FragmentPointScreenBinding ?= null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPointScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


}