package com.example.dailytasktracker.view

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.ActivityMainBinding
import com.example.dailytasktracker.viewModel.MainActivityViewModel
import com.google.android.material.badge.BadgeDrawable

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
                badge.badgeTextColor = ContextCompat.getColor(this,R.color.md_on_surface)
            }else{
                badge.badgeTextColor = getColor(R.color.md_surface)
            }

        }

    }
}