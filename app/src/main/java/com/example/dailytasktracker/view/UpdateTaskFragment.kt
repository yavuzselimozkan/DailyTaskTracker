package com.example.dailytasktracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.FragmentUpdateTaskBinding
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.viewModel.UpdateTaskViewModel

class UpdateTaskFragment : Fragment() {
    private var _binding:FragmentUpdateTaskBinding ?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: UpdateTaskViewModel
    private var currentTask : Task? =null
    private var id =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateTaskBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UpdateTaskViewModel::class.java]

        arguments?.let { bundle->
            id = UpdateTaskFragmentArgs.fromBundle(bundle).id
            viewModel.getTaskById(id)
        }

        observeLiveData()

        binding.btnUpdate.setOnClickListener { updateTask(it,currentTask) }
    }

    private fun observeLiveData(){
        viewModel.updatedLiveData.observe(viewLifecycleOwner){task->
            currentTask = task
            binding.editTextTaskName.setText(task.taskName)
            binding.editTextTaskDesc.setText(task.taskDesc)
        }
    }

    private fun updateTask(view:View,currentTask:Task?){
        currentTask?.let {task->
            if(binding.editTextTaskName.text.isNotEmpty() && binding.editTextTaskDesc.text.isNotEmpty()){
                //TODO("Bu işlemi edit text in değeri değişmesini dinleyen bir yapı olabilir.")
                val taskName = binding.editTextTaskName.text.toString()
                val taskDesc = binding.editTextTaskDesc.text.toString()
                val updatedTask = task.copy(taskName=taskName, taskDesc= taskDesc).apply { taskId = task.taskId }

                println("UpdateFragment - Mevcut Task ID: ${task.taskId}, Yeni Task ID: ${updatedTask.taskId}")
                viewModel.updateTask(updatedTask)
                Toast.makeText(view.context,"Görev başarıyla güncellendi.",Toast.LENGTH_LONG).show()
                findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                binding.btnUpdate.isEnabled = false
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}