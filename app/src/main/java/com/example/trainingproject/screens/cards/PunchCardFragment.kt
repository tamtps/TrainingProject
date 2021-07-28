package com.example.trainingproject.screens.cards

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingproject.R
import com.example.trainingproject.api.RetrofitClient
import com.example.trainingproject.components.WalletCouponAdapter
import com.example.trainingproject.databinding.FragmentPointScreenBinding
import com.example.trainingproject.databinding.FragmentPunchCardScreenBinding
import com.example.trainingproject.databinding.FragmentWalletCouponsScreenBinding
import com.example.trainingproject.models.Coupon
import com.example.trainingproject.models.CouponResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class PunchCardFragment : Fragment() {
    private var _binding : FragmentPunchCardScreenBinding?= null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPunchCardScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        var prefs : SharedPreferences = requireActivity().getSharedPreferences("prefs", MODE_PRIVATE)
        var token = prefs.getString("token",null)
        return view
    }


}