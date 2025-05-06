package com.example.dailytasktracker.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.dailytasktracker.R
import com.example.dailytasktracker.onboard.screens.FifthScreen
import com.example.dailytasktracker.onboard.screens.FirstScreen
import com.example.dailytasktracker.onboard.screens.FourthScreen
import com.example.dailytasktracker.onboard.screens.SecondScreen
import com.example.dailytasktracker.onboard.screens.ThirdScreen

class ViewPagerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),SecondScreen(),ThirdScreen(),FourthScreen(),FifthScreen()
        )
        val viewPageAdapter = ViewPageAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = viewPageAdapter
        return view
    }
}