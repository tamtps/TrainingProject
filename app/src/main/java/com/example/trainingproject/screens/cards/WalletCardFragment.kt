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
import com.example.trainingproject.components.WalletCardAdapter
import com.example.trainingproject.databinding.FragmentWalletCardScreenBinding
import com.example.trainingproject.viewmodels.WalletCardViewModel

class WalletCardFragment : Fragment() {
    private lateinit var binding: FragmentWalletCardScreenBinding
    lateinit var walletCardAdapter: WalletCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWalletCardScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        walletCardAdapter = WalletCardAdapter()
        initViewModel()
        binding.recWalletCard.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recWalletCard.adapter = walletCardAdapter

        binding.searchBar.addTextChangedListener(object: TextWatcher {
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

    private fun initViewModel(){
        val viewModel = ViewModelProvider(this).get(WalletCardViewModel::class.java)
        viewModel.getAccountListObserver().observe(viewLifecycleOwner, {
            if(it.accounts.isNotEmpty()){
                walletCardAdapter.setUpdatedData(it.accounts)
            }
            else {
                Toast.makeText(this.context, "ERROR IN GETTING DATA", Toast.LENGTH_LONG).show()
            }
        })

        val prefs = this.context?.getSharedPreferences("prefs", MODE_PRIVATE)
        val token : String = prefs?.getString("token","")!!
        viewModel.makeApiCall(token)
    }
}