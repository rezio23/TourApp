package com.project189.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project189.R
import com.project189.databinding.FragmentHomeBinding
import com.project189.ui.common.BannerAdapter
import com.project189.ui.common.CategoryAdapter
import com.project189.ui.common.TourAdapter
import com.project189.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var tourAdapter: TourAdapter
    private lateinit var popularAdapter: TourAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBanner()
        setupCategories()
        setupTourList()
        setupPopularList()
        setupClickListeners()
        observeData()
    }

    private fun setupBanner() {
        bannerAdapter = BannerAdapter()
        binding.viewPagerBanner.adapter = bannerAdapter
        binding.dotsIndicator.attachTo(binding.viewPagerBanner)
    }

    private fun setupCategories() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setupTourList() {
        tourAdapter = TourAdapter()
        binding.rvTours.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = tourAdapter
        }
    }

    private fun setupPopularList() {
        popularAdapter = TourAdapter()
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun setupClickListeners() {
        binding.tvSeeAllTours.setOnClickListener {
            findNavController().navigate(R.id.cambodiaFragment)
        }
        
        binding.tvSeeAllPopular.setOnClickListener {
            findNavController().navigate(R.id.cambodiaFragment)
        }
    }

    private fun observeData() {
        viewModel.banners.observe(viewLifecycleOwner) { banners ->
            bannerAdapter.submitList(banners)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.submitList(categories)
        }

        viewModel.allItems.observe(viewLifecycleOwner) { items ->
            tourAdapter.submitList(items)
        }

        viewModel.popularItems.observe(viewLifecycleOwner) { popular ->
            popularAdapter.submitList(popular)
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
