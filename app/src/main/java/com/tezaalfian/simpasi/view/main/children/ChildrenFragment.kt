package com.tezaalfian.simpasi.view.main.children

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.core.data.Resource
import com.tezaalfian.simpasi.core.ui.ChildViewModelFactory
import com.tezaalfian.simpasi.core.ui.ListChildAdapter
import com.tezaalfian.simpasi.databinding.FragmentChildrenBinding

class ChildrenFragment : Fragment() {

    private var _binding: FragmentChildrenBinding? = null
    private lateinit var childrenViewModel: ChildrenViewModel
    private var token = ""

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentChildrenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ChildViewModelFactory.getInstance(requireActivity())
        childrenViewModel =
            ViewModelProvider(this, factory)[ChildrenViewModel::class.java]

        binding?.btnAddChildren?.setOnClickListener {
            val intent = Intent(activity, AddEditChildActivity::class.java)
            intent.putExtra(AddEditChildActivity.STATE, "add")
            startActivity(intent)
        }

        childrenViewModel.getToken().observe(requireActivity()){
            token = it
            loadData(it)
        }
    }

    private fun loadData(token: String) {
        val childAdapter = ListChildAdapter()
        binding?.rvChildren?.adapter = childAdapter
        childrenViewModel.getChildren(token).observe(viewLifecycleOwner){ child ->
            if (child != null) {
                when(child) {
                    is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        childAdapter.setData(child.data)
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            child.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}