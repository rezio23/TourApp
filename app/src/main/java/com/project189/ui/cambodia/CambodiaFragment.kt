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
        observeData()
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
