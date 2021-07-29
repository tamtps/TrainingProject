package com.example.trainingproject.screens.cards

import android.content.Context.MODE_PRIVATE
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingproject.bases.BaseFragment
import com.example.trainingproject.components.WalletCouponAdapter
import com.example.trainingproject.databinding.FragmentWalletCouponsScreenBinding
import com.example.trainingproject.viewmodels.CouponCardViewModel


class WalletCouponsFragment : BaseFragment<FragmentWalletCouponsScreenBinding>(FragmentWalletCouponsScreenBinding::inflate){
    lateinit var couponCardAdapter: WalletCouponAdapter

    override fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(CouponCardViewModel::class.java)
        viewModel.getCouponListObserver().observe(viewLifecycleOwner, {
            if (it.result.isNotEmpty()) {
                couponCardAdapter.setUpdatedData(it.result)
                binding.progressCircularCoupon.visibility = View.INVISIBLE
            } else {
                Toast.makeText(this.context, "ERROR IN GETTING DATA", Toast.LENGTH_LONG).show()
            }
        })

        val prefs = this.context?.getSharedPreferences("prefs", MODE_PRIVATE)
        val token: String = prefs?.getString("token", "")!!
        viewModel.makeApiCall(token, "1368","","-1","1", "100")
    }

    override fun initAdapter() {
        couponCardAdapter = WalletCouponAdapter()
    }

    override fun bindingComponent() {
        binding.listCoupon.layoutManager = LinearLayoutManager(binding.root.context)
        binding.listCoupon.adapter = couponCardAdapter
    }

    override fun bindingSearchBar() {
            binding.searchBarCoupon.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    couponCardAdapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {
                    couponCardAdapter.filter.filter(s)
                }

            })
    }

}