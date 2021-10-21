package com.example.todo.utils.helpers

import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {

    fun getSelectedTheme(): Int {
        return AppCompatDelegate.getDefaultNightMode()
    }

    fun setThemeToFollowSystem() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun setThemeToLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun setThemeToDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

}