package com.example.trainingproject.screens.cards

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingproject.R
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.bases.BaseFragment
import com.example.trainingproject.components.WalletCardAdapter
import com.example.trainingproject.databinding.FragmentWalletCardScreenBinding
import com.example.trainingproject.viewmodels.WalletCardViewModel

class WalletCardFragment : BaseFragment<FragmentWalletCardScreenBinding, WalletCardViewModel>() {
    lateinit var walletCardAdapter: WalletCardAdapter

    override fun initViewModel() {
        viewModel.init("All", "", "", "")
        viewModel.getListObserver().observe(viewLifecycleOwner, {
            walletCardAdapter = WalletCardAdapter()
            binding.recWalletCard.layoutManager = LinearLayoutManager(binding.root.context)
            binding.recWalletCard.adapter = walletCardAdapter
            if (it.accounts.isNotEmpty()) {
                binding.progressCircularWalletCard.visibility = View.INVISIBLE

                walletCardAdapter.setUpdatedData(it.accounts)

            } else {
                var dialog = BaseDialog(requireContext())
                dialog.setContentView()
                dialog.errorDialog(getString(R.string.error_getting_data))
            }
        })

        viewModel.makeApiCall()
    }


    override fun bindingSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                walletCardAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                walletCardAdapter.filter.filter(s)
            }

        })
    }

    override fun getViewModelClass(): Class<WalletCardViewModel> = WalletCardViewModel::class.java

    override fun getViewBinding(): FragmentWalletCardScreenBinding = FragmentWalletCardScreenBinding.inflate(layoutInflater)


}