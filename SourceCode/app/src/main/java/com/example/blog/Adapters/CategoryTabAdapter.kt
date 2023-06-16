package com.example.blog.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.blog.CategoryFragmentFashion
import com.example.blog.CategoryFragmentFood
import com.example.blog.CategoryFragmentHealth
import com.example.blog.CategoryFragmentTravel

class CategoryTabAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategoryFragmentTravel()
            1 -> CategoryFragmentFood()
            2 -> CategoryFragmentHealth()
            3 -> CategoryFragmentFashion()
            else -> throw IllegalArgumentException("Invalid tab position: position")
        }
    }
}