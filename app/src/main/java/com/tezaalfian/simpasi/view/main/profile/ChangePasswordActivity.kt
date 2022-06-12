package com.tezaalfian.simpasi.view.main.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.ui.UserViewModelFactory
import com.tezaalfian.simpasi.core.utils.animateVisibility
import com.tezaalfian.simpasi.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var id: String
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.btnChangePassword.setOnClickListener {
            change()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtOldPassword.isEnabled = !isLoading
            edtNewPassword.isEnabled = !isLoading
            edtConfirmPassword.isEnabled = !isLoading
            btnChangePassword.isEnabled = !isLoading

            if (isLoading) {
                viewProgressbar.animateVisibility(true)
            } else {
                viewProgressbar.animateVisibility(false)
            }
        }
    }

    private fun setupViewModel() {
        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        profileViewModel.getUser().observe(this) { user ->
            token = user.token
            id = user.id
        }
    }

    private fun change(){
        val newPassword = binding.edtNewPassword.text.toString().trim()
        val oldPassword = binding.edtOldPassword.text.toString().trim()
        val confirmPassword = binding.edtConfirmPassword.text.toString().trim()

        when{
            newPassword.isEmpty() -> {
                binding.edtNewPassword.error = resources.getString(R.string.message_validation, "new password")
            }
            oldPassword.isEmpty() -> {
                binding.edtOldPassword.error = resources.getString(R.string.message_validation, "old password")
            }
            confirmPassword.isEmpty() -> {
                binding.edtConfirmPassword.error = resources.getString(R.string.message_validation, "confirm password")
            }

            else ->{
                profileViewModel.changePassword(token, id, newPassword, confirmPassword).observe(this){ result ->
                    if (result != null){
                        when(result) {
                            is Resource.Loading -> {
                                showLoading(true)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
                                finish()
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