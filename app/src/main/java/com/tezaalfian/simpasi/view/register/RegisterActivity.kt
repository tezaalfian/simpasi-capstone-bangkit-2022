package com.tezaalfian.simpasi.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.ui.UserViewModelFactory
import com.tezaalfian.simpasi.core.utils.animateVisibility
import com.tezaalfian.simpasi.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupAction()
        setupViewModel()
    }

    private fun setupAction(){
        binding.btnSignup.setOnClickListener{
            signUp()
        }

        binding.tvSignupLogin.setOnClickListener {
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtSignupEmail.isEnabled = !isLoading
            edtSignupPassword.isEnabled = !isLoading
            edtSignupName.isEnabled = !isLoading
            edtSignupUsername.isEnabled = !isLoading
            edtSignupConfirmPassword.isEnabled = !isLoading
            btnSignup.isEnabled = !isLoading

            if (isLoading) {
                viewProgressbar.animateVisibility(true)
            } else {
                viewProgressbar.animateVisibility(false)
            }
        }
    }

    private fun setupViewModel() {
        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
    }

    private fun signUp(){
        val name = binding.edtSignupName.text.toString().trim()
        val email = binding.edtSignupEmail.text.toString().trim()
        val username = binding.edtSignupUsername.text.toString().trim()
        val password = binding.edtSignupPassword.text.toString().trim()
        val confirmPassword = binding.edtSignupConfirmPassword.text.toString().trim()

        when {
            name.isEmpty() -> {
                binding.edtSignupName.error =
                    resources.getString(R.string.message_validation, "name")
            }
            email.isEmpty() -> {
                binding.edtSignupEmail.error =
                    resources.getString(R.string.message_validation, "email")
            }
            username.isEmpty() -> {
                binding.edtSignupUsername.error =
                    resources.getString(R.string.message_validation, "username")
            }
            password.isEmpty() -> {
                binding.edtSignupPassword.error =
                    resources.getString(R.string.message_validation, "password")
            }
            confirmPassword.isEmpty() -> {
                binding.edtSignupConfirmPassword.error =
                    resources.getString(R.string.message_validation, "confirm password")
            }
            else -> {
                registerViewModel.register(name, email, username, password, confirmPassword).observe(this){ result ->
                    if (result != null){
                        when(result) {
                            is Resource.Loading -> {
                                showLoading(true)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                    AlertDialog.Builder(this@RegisterActivity).apply {
                                        setTitle(getString(R.string.tittle_alert))
                                        setMessage(getString(R.string.message_success_alert))
                                        setPositiveButton(getString(R.string.next)) { _, _ ->
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                            }
                            is Resource.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    this,
                                    result.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}