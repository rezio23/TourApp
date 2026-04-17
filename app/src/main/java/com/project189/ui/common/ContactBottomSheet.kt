package com.project189.ui.common

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project189.databinding.BottomSheetContactBinding
import com.project189.utils.dialPhone

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
        // Set the phone number directly as the button text
        binding.btnCall.text = phone

        binding.btnCall.setOnClickListener {
            requireContext().dialPhone(phone)
            dismiss()
        }

        binding.btnTelegram.setOnClickListener {
            try {
                // Using your specific link for all Telegram contacts
                val telegramUrl = "https://t.me/vichhean_som_bath"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Telegram not installed or invalid link", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
