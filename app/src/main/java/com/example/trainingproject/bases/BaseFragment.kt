package com.example.trainingproject.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding, VM : ViewModel> : Fragment(){
    open var useSharedViewModel: Boolean = false

    protected lateinit var viewModel: VM
    protected abstract fun getViewModelClass(): Class<VM>

    protected lateinit var binding: VB
    protected abstract fun getViewBinding(): VB


    abstract fun initViewModel()
    abstract fun initAdapter()
    abstract fun bindingComponent()
    abstract fun bindingSearchBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViewModel()
        bindingComponent()
        bindingSearchBar()
    }

    open fun setUpViews() {}

    open fun observeView() {}

    open fun observeData() {}

    private fun init() {
        binding = getViewBinding()
        viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity()).get(
                getViewModelClass()
            )
        } else {
            ViewModelProvider(this).get(getViewModelClass())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}