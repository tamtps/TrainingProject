package com.example.trainingproject.screens.cards

import android.content.Context.MODE_PRIVATE
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingproject.R
import com.example.trainingproject.bases.BaseDialog
import com.example.trainingproject.bases.BaseFragment
import com.example.trainingproject.components.WalletCouponAdapter
import com.example.trainingproject.databinding.FragmentWalletCouponsScreenBinding
import com.example.trainingproject.viewmodels.CouponCardViewModel


class WalletCouponsFragment : BaseFragment<FragmentWalletCouponsScreenBinding, CouponCardViewModel>(){
    var couponCardAdapter : WalletCouponAdapter = WalletCouponAdapter()

    override fun initViewModel() {
        val prefs = this.context?.getSharedPreferences("prefs", MODE_PRIVATE)
        val receiverUserId = prefs!!.getString("uid","")!!
        viewModel.init(receiverUserId,"","-1","1", "100")
        viewModel.getListObserver().observe(viewLifecycleOwner, {

            binding.listCoupon.layoutManager = LinearLayoutManager(binding.root.context)
            binding.listCoupon.adapter = couponCardAdapter
            if (it.result.isNotEmpty()) {
                couponCardAdapter.setUpdatedData(it.result)
                binding.progressCircularCoupon.visibility = View.INVISIBLE
            } else {
                var dialog = BaseDialog(requireContext())
                dialog.setContentView()
                dialog.errorDialog(getString(R.string.error_getting_data))
            }
        })

        viewModel.makeApiCall()
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

    override fun getViewModelClass(): Class<CouponCardViewModel> = CouponCardViewModel::class.java

    override fun getViewBinding(): FragmentWalletCouponsScreenBinding = FragmentWalletCouponsScreenBinding.inflate(layoutInflater)

}