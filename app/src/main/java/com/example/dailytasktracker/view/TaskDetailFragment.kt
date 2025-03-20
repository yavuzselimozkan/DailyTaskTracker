package com.example.dailytasktracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.FragmentTaskDetailBinding
import com.example.dailytasktracker.viewModel.TaskDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskDetailFragment() : BottomSheetDialogFragment() {

    private var _binding:FragmentTaskDetailBinding ?= null
    private val binding get()= _binding!!

    private lateinit var viewModel:TaskDetailViewModel
    private lateinit var navController: NavController
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
        navController = requireActivity().findNavController(R.id.fragmentContainerView)

        arguments?.let{ bundle ->
            id=TaskDetailFragmentArgs.fromBundle(bundle).id
            viewModel.getTaskDetail(id)

            binding.imgDelete.setOnClickListener { deleteTask(it,id) }
        }
        //Bu fonksiyon ile taskLiveData doldu.
        //Dolunca observe olmalı ki değişiklikleri ele alalım ve name, desc gibi alanları observe de dolduralım

        binding.imgFavourite.setOnClickListener { }

        observeLiveData()

    }

    private fun observeLiveData()
    {
        viewModel.taskLiveData.observe(viewLifecycleOwner){
            binding.taskNameDetail.text = it.taskName
            binding.taskDescDetail.text = it.taskDesc
        }
    }

    private fun setFavourite(view:View){
        //TODO
    }

    private fun deleteTask(view: View,id: Int){
        //Silme işlemi gerçekleştirilirken recycler view da güncellendi. Navigate işlemi ile tekrar homeFragment gösterildiği için onViewCreated ta işlemler aynen gerçekleşti.
        //TODO YAŞAM DÖNGÜLERİNİ KONTROL ET. BİRİKMELER OLMASIN
        viewModel.deleteTask(id)
        dismiss()
        navController.navigate(R.id.action_taskDetailFragment_to_homeFragment)
        Toast.makeText(view.context,"Görev başarıyla silindi!",Toast.LENGTH_LONG).show()
        println("Görev silindi! $id")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}