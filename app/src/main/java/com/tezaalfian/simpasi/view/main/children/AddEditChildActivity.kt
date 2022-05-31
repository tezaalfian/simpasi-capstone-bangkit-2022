package com.tezaalfian.simpasi.view.main.children

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.datepicker.MaterialDatePicker
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.domain.model.Child
import com.tezaalfian.simpasi.core.utils.MyDateFormat
import com.tezaalfian.simpasi.databinding.ActivityAddEditChildBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds

class AddEditChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = "Children"

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
        val state = intent.getStringExtra(STATE)
        binding.btnSave.setOnClickListener {
            if (state != null) {
                getData(state)
            }
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
                val child = Child(
                    nama = name,
                    bbBayi = weight.toInt(),
                    tbBayi = height.toInt(),
                    tglLahir = birthday,
                    alergi = alergi,
                    jkBayi = when(gender){
                        R.id.radio_button_1 -> "Laki-laki"
                        R.id.radio_button_2 -> "Perempuan"
                        else -> null},
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

    private fun addChild(child: Child) {
        Log.d("CHILD", child.toString())
    }

    private fun updateChild(child: Child) {
    }

    companion object {
        const val STATE = "add"
    }
}