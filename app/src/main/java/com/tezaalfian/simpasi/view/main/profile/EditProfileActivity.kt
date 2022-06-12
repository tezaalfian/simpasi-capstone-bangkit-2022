package com.tezaalfian.simpasi.view.main.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.ui.UserViewModelFactory
import com.tezaalfian.simpasi.core.utils.animateVisibility
import com.tezaalfian.simpasi.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var id: String
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        setupAction()

    }

    private fun setupAction() {
        binding.btnSaveProfile.setOnClickListener {
            save()
        }
    }

    private fun setupViewModel() {
        val factory : UserViewModelFactory = UserViewModelFactory.getInstance(this)
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        profileViewModel.getUser().observe(this){user ->
            token = user.token
            id = user.id
            binding.edtEditName.setText(user.name)
            binding.edtEditEmail.setText(user.email)
            binding.edtEditUsername.setText(user.username)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtEditName.isEnabled = !isLoading
            edtEditEmail.isEnabled = !isLoading
            edtEditUsername.isEnabled = !isLoading
            btnSaveProfile.isEnabled = !isLoading

            if (isLoading) {
                viewProgressbar.animateVisibility(true)
            } else {
                viewProgressbar.animateVisibility(false)
            }
        }
    }

    private fun save(){
        val name = binding.edtEditName.text.toString().trim()
        val email = binding.edtEditEmail.text.toString().trim()
        val username = binding.edtEditUsername.text.toString().trim()

        when {
            name.isEmpty() -> {
                binding.edtEditName.error = resources.getString(R.string.message_validation, "name")
            }
            email.isEmpty() -> {
                binding.edtEditEmail.error = resources.getString(R.string.message_validation, "email")
            }
            username.isEmpty() -> {
                binding.edtEditUsername.error = resources.getString(R.string.message_validation, "username")
            }
            else -> {
                profileViewModel.editProfile(token, id, name, email, username).observe(this){ result ->
                    if (result != null){
                        when(result) {
                            is Resource.Loading -> {
                                showLoading(true)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                profileViewModel.setProfile(token, id, name, email, username)
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