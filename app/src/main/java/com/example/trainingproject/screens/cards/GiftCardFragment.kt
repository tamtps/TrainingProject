package com.example.trainingproject.screens.cards

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingproject.R
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.bases.BaseFragment
import com.example.trainingproject.components.GiftCardAdapter
import com.example.trainingproject.databinding.FragmentGiftCardScreenBinding
import com.example.trainingproject.viewmodels.GiftCardViewModel

class GiftCardFragment : BaseFragment<FragmentGiftCardScreenBinding, GiftCardViewModel>() {
    override var useSharedViewModel: Boolean = true
    lateinit var giftCardAdapter: GiftCardAdapter

    override fun initAdapter() {
        giftCardAdapter = GiftCardAdapter()
    }

    override fun bindingComponent() {
        binding.recGiftCard.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recGiftCard.adapter = giftCardAdapter
    }

    override fun bindingSearchBar() {
        binding.searchBarGift.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                giftCardAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                giftCardAdapter.filter.filter(s)
            }

        })
    }

    override fun initViewModel() {
        val prefs = this.context?.getSharedPreferences("prefs", MODE_PRIVATE)

        viewModel.init("1368","","1","50", "0")
        viewModel.getListObserver().observe(viewLifecycleOwner, {
            if (it.result.transDisplay.isNotEmpty()) {
                giftCardAdapter.setUpdatedData(it.result.transDisplay)
                binding.progressCircularGiftCard.visibility = View.INVISIBLE
            } else {
                var dialog = BaseDialog(requireContext())
                dialog.setContentView()
                dialog.errorDialog(getString(R.string.error_getting_data))
            }
        })

        viewModel.makeApiCall()
    }

    override fun getViewModelClass(): Class<GiftCardViewModel>  = GiftCardViewModel::class.java

    override fun getViewBinding(): FragmentGiftCardScreenBinding = FragmentGiftCardScreenBinding.inflate(layoutInflater)


}