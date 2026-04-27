package com.project189.ui.cambodia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project189.databinding.FragmentCambodiaBinding
import com.project189.ui.common.TourAdapter
import com.project189.viewmodel.CambodiaViewModel

class CambodiaFragment : Fragment() {

    private var _binding: FragmentCambodiaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CambodiaViewModel by viewModels()
    private lateinit var tourAdapter: TourAdapter
    private lateinit var filterAdapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCambodiaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTourList()
        setupFilters()
        setupToggle()
        observeData()

        arguments?.getString("category_name")?.let { category ->
            viewModel.filterByCategory(category)
            filterAdapter.setSelectedCategory(category)
        }
    }

    private fun setupToggle() {
        binding.toggleCountry.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (checkedId != -1 && isChecked) {
                when (checkedId) {
                    binding.btnAll.id -> {
                        viewModel.setCountry("All")
                        binding.tvCountryTitle.text = "All Destinations"
                        binding.tvCountrySubtitle.text = "Explore the best of Southeast Asia"
                    }
                    binding.btnCambodia.id -> {
                        viewModel.setCountry("Cambodia")
                        binding.tvCountryTitle.text = "Cambodia"
                        binding.tvCountrySubtitle.text = "Kingdom of Wonder"
                    }
                    binding.btnPhilippines.id -> {
                        viewModel.setCountry("Philippines")
                        binding.tvCountryTitle.text = "Philippines"
                        binding.tvCountrySubtitle.text = "Pearl of the Orient"
                    }
                    binding.btnIndonesia.id -> {
                        viewModel.setCountry("Indonesia")
                        binding.tvCountryTitle.text = "Indonesia"
                        binding.tvCountrySubtitle.text = "Emerald of the Equator"
                    }
                }
            }
        }
    }

    private fun setupTourList() {
        tourAdapter = TourAdapter()
        binding.rvCambodiaDestinations.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tourAdapter
        }
    }

    private fun setupFilters() {
        val filters = listOf("All", "Beach", "Camp", "Jungle", "Mountain")
        filterAdapter = FilterAdapter(filters) { category ->
            viewModel.filterByCategory(category)
        }

        binding.rvFilters.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
        }
    }

    private fun observeData() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            tourAdapter.submitList(items)
            binding.tvNoResults.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
