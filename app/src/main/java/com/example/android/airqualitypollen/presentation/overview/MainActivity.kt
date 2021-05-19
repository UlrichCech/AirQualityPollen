package com.example.android.airqualitypollen.presentation.overview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.android.airqualitypollen.R
import com.example.android.airqualitypollen.databinding.ActivityMainBinding
import com.example.android.airqualitypollen.business.configuration.GlobalAppConfiguration

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // read the AMBEE-API-Key from launch-flags: -e "AMBEE_API_KEY" "<CUSTOM-API-KEY>"

        if (intent?.extras != null) {
            GlobalAppConfiguration.ambeeApiKey = intent?.extras?.getString("AMBEE_API_KEY", "")
            GlobalAppConfiguration.unsplashApiKey = intent?.extras?.getString("UNSPLASH_API_KEY", "")
        } else {
            GlobalAppConfiguration.ambeeApiKey = ""
            GlobalAppConfiguration.unsplashApiKey = ""
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}