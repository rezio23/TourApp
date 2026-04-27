package com.project189.ui.cambodia

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project189.R
import com.project189.databinding.ItemFilterBinding

class FilterAdapter(
    private val filters: List<String>,
    private val onFilterSelected: (String) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var selectedPosition = 0

    fun setSelectedCategory(category: String) {
        val position = filters.indexOf(category)
        if (position != -1) {
            val oldPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(filter: String, position: Int) {
            binding.tvFilterName.text = filter
            
            val isSelected = selectedPosition == position
            val context = binding.root.context
            
            if (isSelected) {
                binding.cardFilter.setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                binding.tvFilterName.setTextColor(Color.WHITE)
                binding.cardFilter.strokeWidth = 0
            } else {
                // Adapt to theme colors (Dark mode aware)
                val bgColor = ContextCompat.getColor(context, R.color.white)
                val textColor = ContextCompat.getColor(context, R.color.text_secondary)
                
                binding.cardFilter.setCardBackgroundColor(bgColor)
                binding.tvFilterName.setTextColor(textColor)
                binding.cardFilter.strokeWidth = 2
            }
            
            binding.root.setOnClickListener {
                if (selectedPosition != position) {
                    val oldPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(oldPosition)
                    notifyItemChanged(selectedPosition)
                    onFilterSelected(filter)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(filters[position], position)
    }

    override fun getItemCount() = filters.size
}
