package com.example.dailytasktracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailytasktracker.R
import com.example.dailytasktracker.adapter.FavTaskRecyclerAdapter
import com.example.dailytasktracker.databinding.FragmentFavouriteTaskBinding
import com.example.dailytasktracker.databinding.FragmentNewTaskBinding
import com.example.dailytasktracker.viewModel.FavouriteTaskViewModel

class FavouriteTaskFragment : Fragment() {
    private var _binding:FragmentFavouriteTaskBinding ?= null
    private val binding get() = _binding!!

    private var adapter = FavTaskRecyclerAdapter(arrayListOf())
    private lateinit var viewModel:FavouriteTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteTaskBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FavouriteTaskViewModel::class.java]
        viewModel.getAllFavTask()

        binding.favRecyclerView.adapter = adapter
        binding.favRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //TODO("Buraya gelen favori taskları tamamlama fonksiyonu")
        //TODO("Favori taskın üstüne basınca taskDetilFragment açılmalı mı?")

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.favTaskLiveData.observe(viewLifecycleOwner){
            adapter.updateFavList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}