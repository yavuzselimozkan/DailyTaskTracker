package com.example.dailytasktracker.util

import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class NotificationPermissionHelper(private val fragment: Fragment) {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    fun requestNotify(view:View){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(fragment.requireContext(),android.Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                //izin verilmediyse
                if(ActivityCompat.shouldShowRequestPermissionRationale(fragment.requireActivity(),android.Manifest.permission.POST_NOTIFICATIONS)){
                    //izin istenecek
                    Snackbar.make(view,"Permission needed for notifications", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission"){
                        permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }.show()
                }else{
                    //android göstermezse
                    permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            else{
                //izin verildiyse
                NotificationUtil.scheduleNotification(fragment.requireContext().applicationContext)
                SettingsManager.setNotificationsEnabled(fragment.requireContext(),true)
            }
        }else{
            //Tiramusu altı izin gerekmiyor
            SettingsManager.setNotificationsEnabled(fragment.requireContext(),true)
            NotificationUtil.scheduleNotification(fragment.requireContext().applicationContext)
        }
    }

    fun registerLauncher(){
        permissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){ isGranted->
           if(isGranted){
               SettingsManager.setNotificationsEnabled(fragment.requireContext(),true)
               NotificationUtil.scheduleNotification(fragment.requireContext().applicationContext)
           }else{
               //permission denied
               SettingsManager.setNotificationsEnabled(fragment.requireContext(),false)
               Toast.makeText(fragment.requireContext(), "Permisson needed!", Toast.LENGTH_LONG).show()
           }
        }
    }
}