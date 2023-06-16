package com.example.blog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blog.databinding.FragmentSplashBinding


private var _binding : FragmentSplashBinding? = null
private val binding get() = _binding!!
class SplashFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setCurrentFragmentTag("fragment12")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        val view = binding.root

        Handler(Looper.getMainLooper()).postDelayed({
                if (isAuthDone()) {

                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
                else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
        }, 3000)

        return view

    }

    private fun isAuthDone(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}