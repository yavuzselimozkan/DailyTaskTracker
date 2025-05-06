package com.example.dailytasktracker.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.FragmentTaskDetailBinding
import com.example.dailytasktracker.viewModel.TaskDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskDetailFragment() : BottomSheetDialogFragment() {

    private var _binding:FragmentTaskDetailBinding ?= null
    private val binding get()= _binding!!

    private lateinit var viewModel:TaskDetailViewModel
    private lateinit var navController: NavController
    private var id=0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheet = (dialogInterface as BottomSheetDialog)
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.background = ColorDrawable(Color.TRANSPARENT)
        }
        return dialog
    }

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
        println("TaskDetail onCreateView Called!")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TaskDetailViewModel::class.java]
        navController = requireActivity().findNavController(R.id.fragmentContainerView)

        arguments?.let{ bundle ->
            id=TaskDetailFragmentArgs.fromBundle(bundle).id

            viewModel.getTaskDetail(id) //bottomSheet açılınca favori durumu ve diğer durumlar yüklenecek. Observe çalışacak çünkü value değeri atandı

            binding.imgDelete.setOnClickListener { deleteTask(it,id) } //tuşa basınca silecek
            println("gelen id $id")
            binding.imgFavourite.setOnClickListener { setFavouriteTask(id) }//tuşa basınca favorileyecek
            binding.imgUpdate.setOnClickListener { goUpdatePage(id) }
        }
        //Bu fonksiyon ile taskLiveData doldu.
        //Dolunca observe olmalı ki değişiklikleri ele alalım ve name, desc gibi alanları observe de dolduralım

        println("açılan id $id")


        observeLiveData()

    }

    private fun observeLiveData()
    {
        viewModel.taskLiveData.observe(viewLifecycleOwner){
            binding.taskNameDetail.text = it.taskName
            binding.taskDescDetail.text = it.taskDesc
            println("task fav ${viewModel.taskLiveData.value?.isFavourite}")
            if(it.isFavourite){
                //task a basıp fifth_screen açıldığında (yüklendiğinde) bu tetiklenecek ve taskLivedata observe olacak. Favorililik gözükecek
                binding.imgFavourite.setImageResource(R.drawable.favorite_red)
            }else{
                binding.imgFavourite.setImageResource(R.drawable.favorite_border)
            }
        }

        viewModel.taskFavLiveData.observe(viewLifecycleOwner){
            //bu da tuşa bastığım zaman value atanacak ve observe tetiklenecek bu sayede kalp orada dolacak
            if(it){
                binding.imgFavourite.setImageResource(R.drawable.favorite_red)
            }else{
                binding.imgFavourite.setImageResource(R.drawable.favorite_border)
            }
        }
    }

    private fun setFavouriteTask(id:Int){
        //true gelirse ve tuşa basarsa false olacak. Ya da tam tersi
        viewModel.setFavouriteTask(id)
        lifecycleScope.launch{
            delay(250)
            dismiss()
        }


    }

    private fun deleteTask(view: View,id: Int){
        //Silme işlemi gerçekleştirilirken recycler view da güncellendi.
        // Navigate işlemi ile tekrar homeFragment gösterildiği için onViewCreated ta işlemler aynen gerçekleşti.
        viewModel.deleteTask(id)
        dismiss()
        Toast.makeText(view.context,"Görev başarıyla silindi!",Toast.LENGTH_LONG).show()
        println("Görev silindi! $id")
    }

    private fun goUpdatePage(id: Int){
        dismiss()
        val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToUpdateTaskFragment(id)
        navController.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
        println("onDestroyÇalıştı")
    }

}