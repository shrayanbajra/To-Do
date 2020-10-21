package com.example.todo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todo.R
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch(Main) {
            val oneSecond = 1000L
            delay(oneSecond)
            navigateToNavHostActivity()
            finish()
        }
    }

    private fun navigateToNavHostActivity() {
        val intent = Intent(this, NavHostActivity::class.java)
        startActivity(intent)
    }

}