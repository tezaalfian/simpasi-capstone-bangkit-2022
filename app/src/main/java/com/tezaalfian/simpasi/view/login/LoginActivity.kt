package com.tezaalfian.simpasi.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()



    }
}