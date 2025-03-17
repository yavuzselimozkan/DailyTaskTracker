package com.example.dailytasktracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.FragmentTaskDetailBinding
import com.example.dailytasktracker.viewModel.TaskDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskDetailFragment() : BottomSheetDialogFragment() {

    private var _binding:FragmentTaskDetailBinding ?= null
    private val binding get()= _binding!!

    private lateinit var viewModel:TaskDetailViewModel
    private var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TaskDetailViewModel::class.java]

        arguments?.let{
            id=TaskDetailFragmentArgs.fromBundle(it).taskId
            viewModel.getTaskDetail(id)
        }
        //Bu fonksiyon ile taskLiveData doldu.
        //Dolunca observe olmalı ki değişiklikleri ele alalım ve name, desc gibi alanları observe de dolduralım

        observeLiveData()

    }

    private fun observeLiveData()
    {
        viewModel.taskLiveData.observe(viewLifecycleOwner){
            binding.taskNameDetail.text = it.taskName
            binding.taskDescDetail.text = it.taskDesc
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}