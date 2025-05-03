package com.example.dailytasktracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailytasktracker.R
import com.example.dailytasktracker.adapter.TaskRecyclerAdapter
import com.example.dailytasktracker.databinding.FragmentHomeBinding
import com.example.dailytasktracker.util.NotificationPermissionHelper
import com.example.dailytasktracker.util.SettingsManager
import com.example.dailytasktracker.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!

    private lateinit var adapter : TaskRecyclerAdapter
    private lateinit var viewModel:HomeViewModel
    private lateinit var notificationHelper: NotificationPermissionHelper
    private lateinit var rootView: View

    private var totalTasks = 0
    private var completedTasks = 0

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
        println("HomeFragment onCreateView Called!")

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        adapter = TaskRecyclerAdapter(arrayListOf()){id->
            viewModel.setCompleteTask(id)
        }

        notificationHelper = NotificationPermissionHelper(this)
        notificationHelper.registerLauncher()

        binding.recyclerTaskView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTaskView.adapter = adapter

        //viewModel.getDataFromRoom() flow+asLiveData için gerek yok

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("HomeFragment onViewCreated Called!")

        rootView = requireActivity().findViewById(android.R.id.content)

        observeLiveData()
        //görev ekleyince ilk başta observe liveData çalıştığı için eskisi gözüküyor.
        // Sonrasında getDataFromRoom 1.2 saniye sonra çalıştığı için yeni liste gelmiş oluyor.

        //IsFirstTime Kontrolü
        if(SettingsManager.isFirstTime(requireContext())){
            SettingsManager.setFirstTime(requireContext(),false)
            notificationHelper.requestNotify(rootView)
        }

        //Toolbar Menu
        val toolbar = binding.materialToolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        val menuHost:MenuHost = requireActivity()

        menuHost.addMenuProvider(object:MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.toolbarSettings ->{
                        showSettingDialog(view)
                        true
                    }
                    else->false
                }
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)

    }

    private fun observeLiveData()
    {
        viewModel.taskListLiveData.observe(viewLifecycleOwner){
            println("HomeFragment - Veritabanındaki Güncel Task Listesi: $it")
            //içerik her yenilendiğinde adapterdaki veri güncellenecek - ISFIRSTOBSERVATION kontrolü yap
            binding.recyclerTaskView.visibility = View.VISIBLE
            adapter.updateTaskList(it)
        }

        viewModel.totalTasks.observe(viewLifecycleOwner){total->
            totalTasks = total ?: 0
            updateProgress(totalTasks,completedTasks)
            showRvMessage(total)
        }

        viewModel.completedTasks.observe(viewLifecycleOwner){completed->
            completedTasks = completed ?: 0
            updateProgress(totalTasks,completedTasks)
        }
    }

    private fun updateProgress(total:Int,completed:Int){
        val percent = if(total == 0) 0 else (completed.toFloat() / total*100).toInt()
        //bu sayede her gelişte yeniden dolacak
        binding.progressIndicator.setProgressCompat(0, false)
        binding.progressIndicator.postDelayed({
            binding.progressIndicator.setProgressCompat(percent, true)
        }, 100)

        binding.progressText.text = getString(R.string.percentText, completed, total)
    }

    private fun showSettingDialog(view:View){
        val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
        Navigation.findNavController(view).navigate(action)
    }

    private fun showRvMessage(total:Int){
        if(total>0){
            binding.viewStub.visibility = View.GONE
            binding.recyclerTaskView.visibility = View.VISIBLE
        }else{
            binding.viewStub.visibility = View.VISIBLE
            binding.recyclerTaskView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}