package com.example.bitfitpart1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfitpart1.databinding.FragmentSummaryBinding
import kotlinx.coroutines.launch

class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private val recentEntries = mutableListOf<EntryEntity>()
    private lateinit var adapter: EntryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize RecyclerView for recent entries
        adapter = EntryAdapter(requireContext(), recentEntries)
        binding.recentEntriesRecyclerView.adapter = adapter
        binding.recentEntriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Load statistics and recent entries
        loadStatistics()
        loadRecentEntries()

        // Set up Clear Data button
        binding.clearDataButton.setOnClickListener {
            clearData()
        }
    }

    private fun loadStatistics() {
        lifecycleScope.launch {
            val entries = (requireActivity().application as BitFitApplication).db.entryDao().getAllEntries()
            if (entries.isNotEmpty()) {
                val totalCalories = entries.sumOf { it.calories }
                val averageCalories = totalCalories / entries.size
                val maxCalories = entries.maxOf { it.calories }
                val minCalories = entries.minOf { it.calories }

                binding.totalCaloriesTextView.text = "Total Calories: $totalCalories kcal"
                binding.averageCaloriesTextView.text = "Average Calories: $averageCalories kcal"
                binding.maxCaloriesTextView.text = "Max Calories: $maxCalories kcal"
                binding.minCaloriesTextView.text = "Min Calories: $minCalories kcal"
            } else {
                binding.totalCaloriesTextView.text = "No entries available"
                binding.averageCaloriesTextView.text = ""
                binding.maxCaloriesTextView.text = ""
                binding.minCaloriesTextView.text = ""
            }
        }
    }

    private fun loadRecentEntries() {
        lifecycleScope.launch {
            val entries = (requireActivity().application as BitFitApplication).db.entryDao().getAllEntries()
            if (entries.isNotEmpty()) {
                recentEntries.clear()
                recentEntries.addAll(entries.takeLast(5).reversed())
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun clearData() {
        lifecycleScope.launch {
            (requireActivity().application as BitFitApplication).db.entryDao().deleteAll() // Delete all entries from the database
            recentEntries.clear() // Clear the local list of entries
            adapter.notifyDataSetChanged() // Notify adapter of changes

            // Clear the statistics display
            binding.totalCaloriesTextView.text = "No entries available"
            binding.averageCaloriesTextView.text = ""
            binding.maxCaloriesTextView.text = ""
            binding.minCaloriesTextView.text = ""

            // Provide feedback to the user
            binding.recentEntriesLabel.text = "All data has been cleared."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
