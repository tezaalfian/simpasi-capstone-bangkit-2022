package com.tezaalfian.simpasi.view.main.children

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.model.Bahan
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.ui.ChildViewModelFactory
import com.tezaalfian.simpasi.core.utils.MyDateFormat
import com.tezaalfian.simpasi.databinding.ActivityBahanBinding

class BahanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBahanBinding
    private lateinit var childrenViewModel: ChildrenViewModel
    private lateinit var child: ChildEntity
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Feedback Bahan"

        child = intent.getParcelableExtra<ChildEntity>(EXTRA_CHILD) as ChildEntity

        val factory = ChildViewModelFactory.getInstance(this)
        childrenViewModel =
            ViewModelProvider(this, factory)[ChildrenViewModel::class.java]

        binding.btnSave.setOnClickListener {
            getData()
        }

        token = intent.getStringExtra(EXTRA_TOKEN).toString()
    }

    private fun getData() {
        val roti = binding.edtRotiTawar.text.toString().trim()
        val cumiCumi = binding.edtCumiCumi.text.toString().trim()
        val tepungBeras = binding.edtTepungBeras.text.toString().trim()
        val pisang = binding.edtPisang.text.toString().trim()
        val telurBebek = binding.edtTelurBebek.text.toString().trim()
        val kacangTanah = binding.edtKacangTanah.text.toString().trim()
        val kerang = binding.edtKerang.text.toString().trim()
        val alpukat = binding.edtAlpukat.text.toString().trim()

        binding.apply {
            tvRotiTawar.error = null
            tvCumiCumi.error = null
            tvTepungBeras.error = null
            tvPisang.error = null
            tvTelurBebek.error = null
            tvKacangTanah.error = null
            tvKerang.error = null
            tvAlpukat.error = null
        }

        when {
            roti.isEmpty() -> {
                binding.tvRotiTawar.error = resources.getString(R.string.error_validation, "Roti Tawar")
            }
            cumiCumi.isEmpty() -> {
                binding.tvCumiCumi.error = resources.getString(R.string.error_validation, "Cumi-cumi")
            }
            tepungBeras.isEmpty() -> {
                binding.tvTepungBeras.error = resources.getString(R.string.error_validation, "Tepung Beras")
            }
            pisang.isEmpty() -> {
                binding.tvPisang.error = resources.getString(R.string.error_validation, "Pisang")
            }
            telurBebek.isEmpty() -> {
                binding.tvTelurBebek.error = resources.getString(R.string.error_validation, "Telur Bebek")
            }
            kacangTanah.isEmpty() -> {
                binding.tvKacangTanah.error = resources.getString(R.string.error_validation, "Kacang Tanah")
            }
            kerang.isEmpty() -> {
                binding.tvKerang.error = resources.getString(R.string.error_validation, "Kerang")
            }
            alpukat.isEmpty() -> {
                binding.tvAlpukat.error = resources.getString(R.string.error_validation, "Alpukat")
            }
            else -> {
                val food = Bahan(
                    getIndex(roti), getIndex(cumiCumi), getIndex(tepungBeras), getIndex(pisang),
                    getIndex(telurBebek), getIndex(kacangTanah), getIndex(kerang), getIndex(alpukat)
                )
                childrenViewModel.bahan(token, child.id, food).observe(this){result ->
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
        }
    }

    private fun getIndex(value: String) : String {
        return when(value.lowercase()) {
            "suka" -> "1"
            "tidak suka" -> "2"
            "alergi" -> "3"
            "tidak tahu" -> "4"
            else -> "4"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtAlpukat.isEnabled = !isLoading
            edtCumiCumi.isEnabled = !isLoading
            edtKacangTanah.isEnabled = !isLoading
            edtKerang.isEnabled = !isLoading
            btnSave.isEnabled = !isLoading
            edtPisang.isEnabled = !isLoading
            edtRotiTawar.isEnabled = !isLoading
            edtTelurBebek.isEnabled = !isLoading
            edtTepungBeras.isEnabled = !isLoading

            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        const val EXTRA_CHILD = "extra_child"
        const val EXTRA_TOKEN = "extra_token"
    }
}