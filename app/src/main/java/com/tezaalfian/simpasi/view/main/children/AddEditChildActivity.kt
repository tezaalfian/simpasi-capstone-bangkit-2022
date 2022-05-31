package com.tezaalfian.simpasi.view.main.children

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.source.remote.response.ChildResponse
import com.tezaalfian.simpasi.core.ui.ChildViewModelFactory
import com.tezaalfian.simpasi.core.utils.MyDateFormat
import com.tezaalfian.simpasi.core.utils.animateVisibility
import com.tezaalfian.simpasi.databinding.ActivityAddEditChildBinding

class AddEditChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditChildBinding
    private lateinit var childrenViewModel: ChildrenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Children"

        val state = intent.getStringExtra(STATE).toString()

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
                binding.btnDelete.text = resources.getString(R.string.update)
            }
        }
    }

    private fun getData(state: String) {
        val name = binding.edtName.text.toString().trim()
        val weight = binding.edtWeight.text.toString().trim()
        val height = binding.edtHeight.text.toString().trim()
        val alergi = binding.edtAlergi.text.toString().trim()
        val birthday = binding.edtBirthday.text.toString().trim()
        val gender = binding.egGender.checkedRadioButtonId

        binding.tvName.error = null
        binding.tvWeight.error = null
        binding.tvHeight.error = null
        binding.tvBirthday.error = null

        when {
            name.isEmpty() -> {
                binding.tvName.error = resources.getString(R.string.error_validation, "name")
            }
            weight.isEmpty() -> {
                binding.tvWeight.error = resources.getString(R.string.error_validation, "weight")
            }
            height.isEmpty() -> {
                binding.tvHeight.error = resources.getString(R.string.error_validation, "height")
            }
            birthday.isEmpty() -> {
                binding.tvBirthday.error = resources.getString(R.string.error_validation, "birthday")
            }
            else -> {
                val child = ChildResponse(
                    nama = name,
                    bbBayi = weight.toInt(),
                    tbBayi = height.toInt(),
                    tglLahir = birthday,
                    alergi = alergi,
                    jkBayi = when(gender){
                        R.id.radio_button_1 -> "laki-laki"
                        R.id.radio_button_2 -> "Perempuan"
                        else -> "Laki-laki"},
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
            MyDateFormat.TOKEN, child.nama, child.tglLahir, child.jkBayi, child.tbBayi, child.bbBayi, child.alergi
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

    private fun updateChild(child: ChildResponse) {
    }

    companion object {
        const val STATE = "add"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtName.isEnabled = !isLoading
            edtBirthday.isEnabled = !isLoading
            edtHeight.isEnabled = !isLoading
            edtWeight.isEnabled = !isLoading
            edtAlergi.isEnabled = !isLoading
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
}