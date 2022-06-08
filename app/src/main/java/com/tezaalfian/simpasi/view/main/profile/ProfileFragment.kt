package com.tezaalfian.simpasi.view.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.core.ui.ChildViewModelFactory
import com.tezaalfian.simpasi.core.ui.UserViewModelFactory
import com.tezaalfian.simpasi.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = UserViewModelFactory.getInstance(requireActivity())
        val profileViewModel =
            ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        profileViewModel.getUser().observe(requireActivity()){user ->
            binding?.tvEmailEdit?.text = user.email
            binding?.tvNameEdit?.text = user.name
            binding?.tvUsernameEdit?.text = user.username
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}