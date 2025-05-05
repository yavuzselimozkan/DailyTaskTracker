package com.example.dailytasktracker.onboard.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dailytasktracker.R
import com.example.dailytasktracker.util.SettingsManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //If is first time navigate to viewPager
        val isFirstTime = SettingsManager.isFirstTime(requireContext())

        lifecycleScope.launch {
            if(isFirstTime){
                delay(2500)
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }else{
                delay(2500)
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }

}