package com.example.trainingproject.walk_through
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
class WalkThroughAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 4;
    }
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return  WalkThroughFragment1()
            1 -> return WalkThroughFragment2()
            2 -> return WalkThroughFragment3()
            3 -> return WalkThroughFragment4()
            else -> return  WalkThroughFragment1()
        }
    }
}