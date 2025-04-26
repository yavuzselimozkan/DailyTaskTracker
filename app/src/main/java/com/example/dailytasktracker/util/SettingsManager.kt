package com.example.dailytasktracker.util

import android.content.Context
import android.content.SharedPreferences

object SettingsManager {

    private const val PREF_NAME = "prefs"
    private const val KEY_IS_FIRST_TIME = "is_first_time"
    private const val  KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
    private const val KEY_DARK_THEME_ENABLED = "dark_theme_enabled"

    private fun getPrefs(context: Context) : SharedPreferences{
        return context.applicationContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    }

    fun isFirstTime(context:Context):Boolean{
        return getPrefs(context).getBoolean(KEY_IS_FIRST_TIME,true)
    }

    fun setFirstTime(context: Context,isFirstTime:Boolean){
        getPrefs(context).edit().putBoolean(KEY_IS_FIRST_TIME,isFirstTime).apply()
    }

    //Bildirimler açık mı?
    fun isNotificationsEnabled(context:Context):Boolean{
        return getPrefs(context).getBoolean(KEY_NOTIFICATIONS_ENABLED,false)
    }

    fun setNotificationsEnabled(context:Context,enabled:Boolean){
        getPrefs(context).edit().putBoolean(KEY_NOTIFICATIONS_ENABLED,enabled).apply()
    }

    fun isDarkTheme(context:Context):Boolean{
        return getPrefs(context).getBoolean(KEY_DARK_THEME_ENABLED,false)
    }

    fun setDarkTheme(context:Context,darkTheme:Boolean){
        getPrefs(context).edit().putBoolean(KEY_DARK_THEME_ENABLED,darkTheme).apply()
    }
}