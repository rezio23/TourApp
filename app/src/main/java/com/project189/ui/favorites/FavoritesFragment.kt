package com.project189.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.project189.databinding.FragmentFavoritesBinding
import com.project189.ui.common.FavoriteAdapter
import com.project189.ui.splash.SplashActivity
import com.project189.viewmodel.FavoritesViewModel
import java.util.Locale

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setupRecyclerView()
        setupProfileInfo()
        observeFavorites()
        
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
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
        val user = auth.currentUser
        val username = user?.displayName ?: user?.email?.substringBefore("@") ?: "User"

        // Capitalize the username
        binding.tvUsername.text = username.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

        binding.tvEmail.text = user?.email ?: "No email"
        
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
