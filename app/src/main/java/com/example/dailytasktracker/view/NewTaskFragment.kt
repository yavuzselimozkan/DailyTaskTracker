package com.example.dailytasktracker.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.example.dailytasktracker.databinding.FragmentNewTaskBinding
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.viewModel.NewTaskViewModel

class NewTaskFragment : Fragment() {

    private var _binding : FragmentNewTaskBinding ?= null
    private val binding get() = _binding!!

    private lateinit var viewModel:NewTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewTaskBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NewTaskViewModel::class.java]

        binding.btnSave.setOnClickListener { saveNewTask(it) }

    }

    private fun saveNewTask(view: View)
    {
        if(binding.editTextTaskName.text.isNotEmpty() && binding.editTextDesc.text.isNotEmpty())
        {
            val taskName = binding.editTextTaskName.text.toString()
            val taskDesc = binding.editTextDesc.text.toString()
            val task = Task(taskName,taskDesc)
            viewModel.insertTask(task)
            binding.editTextTaskName.text.clear(); binding.editTextDesc.text.clear()
            Toast.makeText(view.context,"Görev başarıyla eklendi!",Toast.LENGTH_LONG).show()
            //save tuşuna basınca klavye kapanacak
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)

        }else{
            Toast.makeText(view.context,"İsim ve Açıklama boş olamaz!",Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}