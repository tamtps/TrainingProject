package com.example.trainingproject.components

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.trainingproject.R
import com.example.trainingproject.screens.cards.*

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4,
    R.string.tab_text_5
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return WalletCardFragment()
            }

            1 -> {
                return GiftCardFragment()
            }

            2 -> {
                return WalletCouponsFragment()
            }
            3 -> {
                return PunchCardFragment()
            }
        }
        return PointFragment()
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int {
        return 5
    }
}