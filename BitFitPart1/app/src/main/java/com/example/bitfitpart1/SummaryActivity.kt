package com.example.bitfitpart1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfitpart1.databinding.ActivitySummaryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.ImageView

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding
    private val recentEntries = mutableListOf<EntryEntity>()
    private lateinit var adapter: EntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using View Binding
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView for recent entries
        adapter = EntryAdapter(this, recentEntries)
        binding.recentEntriesRecyclerView.adapter = adapter
        binding.recentEntriesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Load statistics and recent entries
        loadStatistics()
        loadRecentEntries()

        // Set up BottomNavigationView
        binding.bottomNavigationView.selectedItemId = R.id.menu_summary
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    // Clear the back stack to prevent multiple instances
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    true
                }
                R.id.menu_summary -> {
                    // Already on the summary screen
                    true
                }
                else -> false
            }
        }
    }

    private fun loadStatistics() {
        lifecycleScope.launch {
            val entries = (application as BitFitApplication).db.entryDao().getAllEntries()
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
            val entries = (application as BitFitApplication).db.entryDao().getAllEntries()
            if (entries.isNotEmpty()) {
                recentEntries.clear()
                recentEntries.addAll(entries.takeLast(5).reversed())
                adapter.notifyDataSetChanged()
            }
        }
    }
}
