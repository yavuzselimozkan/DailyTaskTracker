package com.example.dailytasktracker.onboard.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.FirstScreenBinding

class FirstScreen : Fragment() {

    private var _binding: FirstScreenBinding? = null
    private val binding get() =_binding!!

    private lateinit var viewPager:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FirstScreenBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        binding.btnNext.setOnClickListener {
            viewPager.currentItem = 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}