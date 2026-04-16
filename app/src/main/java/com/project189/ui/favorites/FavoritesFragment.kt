package com.project189.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.project189.databinding.FragmentFavoritesBinding
import com.project189.ui.common.FavoriteAdapter
import com.project189.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupProfileInfo()
        observeFavorites()
        
        binding.btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter { item ->
            viewModel.removeFavorite(item)
        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@FavoritesFragment.adapter
        }
    }

    private fun setupProfileInfo() {
        binding.tvUsername.text = "Rezio23"
        binding.tvEmail.text = "rezio23@tour-app.com"
        
        // Added user profile image using a high-quality placeholder
        Glide.with(this)
            .load("https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=1000&auto=format&fit=crop")
            .circleCrop()
            .into(binding.ivProfile)
    }

    private fun observeFavorites() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
            binding.tvEmpty.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
            binding.tvFavoritesCount.text = favorites.size.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
