package com.example.dailytasktracker.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dailytasktracker.databinding.FragmentNewTaskBinding
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.viewModel.NewTaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewTaskFragment : Fragment() {

    private var _binding : FragmentNewTaskBinding ?= null
    private val binding get() = _binding!!

    private lateinit var viewModel:NewTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NewTaskViewModel::class.java]
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

        binding.btnSave.setOnClickListener { saveNewTask(it) }

        logCurrentTime("Güncel Zaman")
    }

    private fun saveNewTask(view: View)
    {
        if(binding.editTextTaskName.text.isNotEmpty() && binding.editTextDesc.text.isNotEmpty())
        {
            val taskName = binding.editTextTaskName.text.toString().trim()
            val taskDesc = binding.editTextDesc.text.toString().trim()
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

    private fun logCurrentTime(stage: String) {
        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(date)

        Log.d("CurrentTime", "$stage Time: $formattedDate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}