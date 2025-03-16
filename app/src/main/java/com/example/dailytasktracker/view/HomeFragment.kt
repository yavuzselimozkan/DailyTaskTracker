package com.example.dailytasktracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailytasktracker.adapter.TaskRecyclerAdapter
import com.example.dailytasktracker.databinding.FragmentHomeBinding
import com.example.dailytasktracker.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!

    private val adapter = TaskRecyclerAdapter(arrayListOf())
    private lateinit var viewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getDataFromRoom()

        binding.recyclerTaskView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTaskView.adapter = adapter

        observeLiveData()

    }

    private fun observeLiveData()
    {
        viewModel.taskListLiveData.observe(viewLifecycleOwner){
            //içerik her yenilendiğinde adapterdaki veri güncellenecek
            adapter.updateTaskList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}