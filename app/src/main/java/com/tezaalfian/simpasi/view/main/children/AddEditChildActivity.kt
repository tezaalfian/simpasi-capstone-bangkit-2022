package com.tezaalfian.simpasi.view.main.children

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.ui.ChildViewModelFactory
import com.tezaalfian.simpasi.core.utils.MyDateFormat
import com.tezaalfian.simpasi.core.utils.animateVisibility
import com.tezaalfian.simpasi.databinding.ActivityAddEditChildBinding

class AddEditChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditChildBinding
    private lateinit var childrenViewModel: ChildrenViewModel
    private lateinit var child: ChildEntity
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Children"

        val state = intent.getStringExtra(STATE).toString()
        token = intent.getStringExtra(EXTRA_TOKEN).toString()

        val factory = ChildViewModelFactory.getInstance(this)
        childrenViewModel =
            ViewModelProvider(this, factory)[ChildrenViewModel::class.java]

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        binding.edtBirthday.setOnClickListener {
            datePicker.show(supportFragmentManager, "tag")
        }

        datePicker.addOnPositiveButtonClickListener {
            binding.edtBirthday.setText(MyDateFormat.timeToDate(time = it))
        }

        binding.btnSave.setOnClickListener {
            getData(state)
        }
        when(state){
            "add" -> {
                binding.btnDelete.visibility = View.GONE
            }
            "edit" -> {
                val childExtra = intent.getParcelableExtra<ChildEntity>(EXTRA_CHILD)
                if (childExtra != null) {
                    this.child = childExtra
                }
                binding.apply {
                    btnSave.text = resources.getString(R.string.update)
                    edtName.setText(child.nama)
                    edtBirthday.setText(MyDateFormat.myLocalDateFormat(child.tglLahir))
                    edtWeight.setText(child.bbBayi.toString())
                    btnDelete.setOnClickListener {
                        MaterialAlertDialogBuilder(this@AddEditChildActivity)
                            .setTitle(resources.getString(R.string.title_delete))
                            .setMessage(resources.getString(R.string.sure))
                            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->

                            }
                            .setPositiveButton(resources.getString(R.string.title_delete)) { _, _ ->
                                childrenViewModel.deleteChild(token, child.id).observe(this@AddEditChildActivity){result ->
                                    if (result != null) {
                                        when(result) {
                                            is Resource.Loading -> {
                                                showLoading(true)
                                            }
                                            is Resource.Success -> {
                                                showLoading(false)
                                                Toast.makeText(this@AddEditChildActivity, resources.getString(R.string.success), Toast.LENGTH_SHORT).show()
                                                finish()
                                            }
                                            is Resource.Error -> {
                                                showLoading(false)
                                                Toast.makeText(
                                                    this@AddEditChildActivity,
                                                    "Failure : " + result.error,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                            }
                            .show()
                    }
                }
                when(child.jkBayi) {
                    "P" -> binding.radioButton2.isChecked = true
                    "L" -> binding.radioButton1.isChecked = true
                }
            }
        }
    }

    private fun getData(state: String) {
        val name = binding.edtName.text.toString().trim()
        val weight = binding.edtWeight.text.toString().trim()
        val birthday = binding.edtBirthday.text.toString().trim()
        val gender = binding.egGender.checkedRadioButtonId

        binding.tvName.error = null
        binding.tvWeight.error = null
        binding.tvBirthday.error = null

        when {
            name.isEmpty() -> {
                binding.tvName.error = resources.getString(R.string.error_validation, "name")
            }
            weight.isEmpty() -> {
                binding.tvWeight.error = resources.getString(R.string.error_validation, "weight")
            }
            birthday.isEmpty() -> {
                binding.tvBirthday.error = resources.getString(R.string.error_validation, "birthday")
            }
            else -> {
                val child = ChildResponse(
                    nama = name,
                    bbBayi = weight.toInt(),
                    tglLahir = birthday,
                    jkBayi = when(gender){
                        R.id.radio_button_1 -> "L"
                        R.id.radio_button_2 -> "P"
                        else -> "L"},
                    id = "tes",
                    user = "tes"
                )
                when(state) {
                    "add" -> addChild(child)
                    "edit" -> updateChild(child)
                }
            }
        }
    }

    private fun addChild(child: ChildResponse) {
        childrenViewModel.addChild(
            token, child.nama, child.tglLahir, child.jkBayi, child.bbBayi
        ).observe(this){result ->
            if (result != null) {
                when(result) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        Toast.makeText(this, resources.getString(R.string.success), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            this,
                            "Failure : " + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun updateChild(data: ChildResponse) {
        childrenViewModel.editChild(token,
        ChildEntity(
            child.id, data.nama, data.tglLahir, data.bbBayi, child.user, data.jkBayi
        )).observe(this){ result ->
            if (result != null) {
                when(result) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        Toast.makeText(this, resources.getString(R.string.success), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            this,
                            "Failure : " + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtName.isEnabled = !isLoading
            edtBirthday.isEnabled = !isLoading
            edtWeight.isEnabled = !isLoading
            egGender.isEnabled = !isLoading
            btnSave.isEnabled = !isLoading
            btnDelete.isEnabled = !isLoading

            if (isLoading) {
                viewProgressbar.animateVisibility(true)
            } else {
                viewProgressbar.animateVisibility(false)
            }
        }
    }

    companion object {
        const val STATE = "add"
        const val EXTRA_CHILD = "extra_child"
        const val EXTRA_TOKEN = "extra_token"
    }
}