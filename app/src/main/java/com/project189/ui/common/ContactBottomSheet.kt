package com.project189.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project189.databinding.BottomSheetContactBinding
import com.project189.utils.dialPhone
import com.project189.utils.sendSms

class ContactBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetContactBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_PHONE = "arg_phone"
        private const val ARG_NAME = "arg_name"

        fun newInstance(phone: String, name: String): ContactBottomSheet {
            return ContactBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(ARG_PHONE, phone)
                    putString(ARG_NAME, name)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val phone = arguments?.getString(ARG_PHONE) ?: return
        val name = arguments?.getString(ARG_NAME) ?: ""

        binding.tvGuideName.text = name
        binding.tvPhone.text = phone

        binding.btnCall.setOnClickListener {
            requireContext().dialPhone(phone)
            dismiss()
        }

        binding.btnMessage.setOnClickListener {
            requireContext().sendSms(phone)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
