package com.tezaalfian.simpasi.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.ui.UserViewModelFactory
import com.tezaalfian.simpasi.view.login.LoginActivity
import com.tezaalfian.simpasi.view.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        val splashViewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]

        splashViewModel.getAuthToken().observe(this) { token ->
            if (token.isNullOrEmpty()) {
                Intent(this, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            } else {
                Intent(this, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}