package com.example.dailytasktracker.view

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.ActivityMainBinding
import com.example.dailytasktracker.util.SettingsManager
import com.example.dailytasktracker.viewModel.MainActivityViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.color.MaterialColors

class MainActivity : AppCompatActivity() {

    private val activityViewModel : MainActivityViewModel by viewModels()
    private lateinit var badge : BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DTT)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomMenu,navHostFragment.navController)

        badge = binding.bottomMenu.getOrCreateBadge(R.id.favouriteTaskFragment)
        badge.maxCharacterCount = 2
        badge.backgroundColor = Color.TRANSPARENT


        //Favourite Icon Badge
        activityViewModel.favouriteCount.observe(this){count->
            badge.isVisible = count>0
            badge.number = count
        }

        //BadgeIsActive
        navHostFragment.navController.addOnDestinationChangedListener{_,destination,_ ->
            val isActive = destination.id == R.id.favouriteTaskFragment
            if(isActive){
                badge.badgeTextColor = MaterialColors.getColor(this,R.attr.colorOnSurface,Color.BLACK)
            }else{
                badge.badgeTextColor = MaterialColors.getColor(this, R.attr.colorOnSurface,Color.BLACK)
            }

        }

        //Light-Dark Theme
        val isDark = SettingsManager.isDarkTheme(applicationContext)
        if(isDark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }
}