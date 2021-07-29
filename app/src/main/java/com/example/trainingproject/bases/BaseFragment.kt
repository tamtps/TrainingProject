package com.example.trainingproject.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(private val inflate: Inflate<VB>) : Fragment(){
    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun initViewModel()
    abstract fun initAdapter()
    abstract fun bindingComponent()
    abstract fun bindingSearchBar()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViewModel()
        bindingComponent()
        bindingSearchBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}