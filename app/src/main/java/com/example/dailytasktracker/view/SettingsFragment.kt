package com.example.dailytasktracker.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.dailytasktracker.databinding.FragmentSettingsBinding
import com.example.dailytasktracker.util.NotificationPermissionHelper
import com.example.dailytasktracker.util.NotificationUtil
import com.example.dailytasktracker.util.SettingsManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingsFragment : BottomSheetDialogFragment() {

    private var _binding:FragmentSettingsBinding ?=null
    private val binding get() = _binding!!

    private lateinit var notificationHelper: NotificationPermissionHelper

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
        notificationHelper = NotificationPermissionHelper(this)
        notificationHelper.registerLauncher()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Switch Kontrolü
        val isChecked = SettingsManager.isNotificationsEnabled(requireContext())
        binding.notificationSwitch.isChecked = isChecked

        binding.notificationSwitch.setOnCheckedChangeListener { _, checked ->
            if(checked){
                notificationHelper.requestNotify(view)
            }else{
                SettingsManager.setNotificationsEnabled(requireContext(),false)
                NotificationUtil.cancelNotification(requireContext())
                Toast.makeText(requireContext(),"Bildirimler sessize alındı",Toast.LENGTH_LONG).show()
            }
        }

        //Dark Theme Kontrolü
        val isDark = SettingsManager.isDarkTheme(requireContext())
        binding.darkThemeSwitch.isChecked = isDark

        binding.darkThemeSwitch.setOnCheckedChangeListener{_,dark->
            if(dark){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SettingsManager.setDarkTheme(requireContext(),true)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SettingsManager.setDarkTheme(requireContext(),false)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}