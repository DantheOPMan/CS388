package com.example.bitfitpart1

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfitpart1.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val entries = mutableListOf<EntryEntity>()
    private lateinit var adapter: EntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        adapter = EntryAdapter(this, entries)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Load data from database
        lifecycleScope.launch {
            (application as BitFitApplication).db.entryDao().getAll().collect { databaseList ->
                entries.clear()
                entries.addAll(databaseList)
                adapter.notifyDataSetChanged()
            }
        }

        // Handle FloatingActionButton click to add a new entry
        binding.fab.setOnClickListener {
            showAddEntryDialog()
        }

        // Set up BottomNavigationView
        binding.bottomNavigationView.selectedItemId = R.id.menu_home
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Already on the home screen
                    true
                }
                R.id.menu_summary -> {
                    val intent = Intent(this, SummaryActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun showAddEntryDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_entry, null)
        val foodNameEditText = dialogView.findViewById<EditText>(R.id.foodNameEditText)
        val caloriesEditText = dialogView.findViewById<EditText>(R.id.caloriesEditText)

        AlertDialog.Builder(this)
            .setTitle("Add Entry")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val foodName = foodNameEditText.text.toString()
                val calories = caloriesEditText.text.toString().toIntOrNull() ?: 0

                // Input validation
                if (foodName.isNotBlank() && calories > 0) {
                    val newEntry = EntryEntity(
                        foodName = foodName,
                        calories = calories
                    )

                    lifecycleScope.launch {
                        (application as BitFitApplication).db.entryDao().insert(newEntry)
                    }
                } else {
                    // Show an error message if input is invalid
                    AlertDialog.Builder(this)
                        .setTitle("Invalid Input")
                        .setMessage("Please enter a valid food name and calorie amount.")
                        .setPositiveButton("OK", null)
                        .create()
                        .show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}
