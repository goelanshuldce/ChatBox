package com.anshul.chatbook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.anshul.chatbook.databinding.ActivityMainBinding
import com.anshul.chatbook.service.SyncService
import com.anshul.login.ILoginFragmentNavigation
import com.anshul.utils.NetworkState
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), ILoginFragmentNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, MainActivityViewModelFactory(application)).get(
            MainActivityViewModel::class.java
        )
        val navController = this.findNavController(R.id.mainNavigationFragment)

        NavigationUI.setupActionBarWithNavController(this,navController)

        viewModel.authToken.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                navController.popBackStack()
                navController.navigate(R.id.login_navigation)
            } else {
                navController.popBackStack()
                navController.navigate(R.id.active_chat_navigation)
            }
        })

        // SnackBar for No connectivity
        viewModel.networkState.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.not_connected),
                Snackbar.LENGTH_INDEFINITE
            )
            if (it != NetworkState.CONNECTED) {
                snackbar.show()
            } else {
                snackbar.dismiss()
            }
        })

        startService(Intent(applicationContext, SyncService::class.java))
    }

    override fun loginSuccessful() {
        val navController = this.findNavController(R.id.mainNavigationFragment)
        navController.popBackStack()
        navController.navigate(R.id.active_chat_navigation)
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(applicationContext, SyncService::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        return this.findNavController(R.id.mainNavigationFragment).navigateUp() || super.onSupportNavigateUp()
    }

}