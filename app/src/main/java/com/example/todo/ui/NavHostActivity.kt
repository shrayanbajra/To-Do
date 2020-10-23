package com.example.todo.ui

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.todo.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class NavHostActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var someString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableNavigateUp()
        Log.d("NavHost:", someString)
    }

    private fun enableNavigateUp() {
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}
