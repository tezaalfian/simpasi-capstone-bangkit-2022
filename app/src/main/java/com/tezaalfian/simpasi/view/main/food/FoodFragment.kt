package com.tezaalfian.simpasi.view.main.food

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.core.ui.FoodViewModelFactory
import com.tezaalfian.simpasi.core.ui.ListFoodDailyAdapter
import com.tezaalfian.simpasi.core.utils.MyDateFormat
import com.tezaalfian.simpasi.databinding.FragmentFoodBinding
import java.text.SimpleDateFormat
import java.util.*

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private lateinit var foodViewModel: FoodViewModel
    private var token: String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = FoodViewModelFactory.getInstance(requireActivity())
        foodViewModel = ViewModelProvider(requireActivity(), factory)[FoodViewModel::class.java]
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val currentDate = sdf.format(Date())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        binding?.edtDate?.setOnClickListener {
            activity?.supportFragmentManager?.let { it1 -> datePicker.show(it1, "tag") }
        }
        datePicker.addOnPositiveButtonClickListener {
            binding?.edtDate?.setText(MyDateFormat.timeToDate(time = it))
        }
        binding?.edtDate?.setText(currentDate)

        binding?.edtDate?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (token.isNotEmpty()) {
                    loadData(s.toString(), token)
                }
            }
        })
        foodViewModel.getToken().observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                token = it
                loadData(currentDate, token)
            }
        }
    }

    private fun loadData(date: String, token: String) {
        val foodAdapter = ListFoodDailyAdapter()
        binding?.rvMenu?.adapter = foodAdapter
        foodViewModel.getFood(date, token).observe(viewLifecycleOwner){result ->
            if (result != null) {
                when(result) {
                    is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        foodAdapter.setData(result.data)
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        foodAdapter.setOnItemClickCallback(object : ListFoodDailyAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FoodEntity) {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle(resources.getString(R.string.title_delete))
                    .setMessage(resources.getString(R.string.sure))
                    .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
                    .setPositiveButton(resources.getString(R.string.title_delete)) { _, _ ->
                        foodViewModel.deleteFood(data)
                    }
                    .show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}