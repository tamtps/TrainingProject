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
import com.example.trainingproject.bases.BaseFragment
import com.example.trainingproject.components.GiftCardAdapter
import com.example.trainingproject.databinding.FragmentGiftCardScreenBinding
import com.example.trainingproject.viewmodels.GiftCardViewModel

class GiftCardFragment : BaseFragment<FragmentGiftCardScreenBinding>(FragmentGiftCardScreenBinding::inflate) {
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
        val viewModel = ViewModelProvider(this).get(GiftCardViewModel::class.java)
        viewModel.getTransListObserver().observe(viewLifecycleOwner, {
            if (it.transDisplay.isNotEmpty()) {
                giftCardAdapter.setUpdatedData(it.transDisplay)
                binding.progressCircularGiftCard.visibility = View.INVISIBLE
            } else {
                Toast.makeText(this.context, "ERROR IN GETTING DATA", Toast.LENGTH_LONG).show()
                //TODO: SHOW DIALOG MESSAGE
            }
        })

        val prefs = this.context?.getSharedPreferences("prefs", MODE_PRIVATE)
        val token: String = prefs?.getString("token", "")!!
        viewModel.makeApiCall(token, "1368","","1","50", "0")
    }


}