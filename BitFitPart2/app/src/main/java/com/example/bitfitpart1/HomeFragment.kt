package com.example.bitfitpart1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfitpart1.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val entries = mutableListOf<EntryEntity>()
    private lateinit var adapter: EntryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up RecyclerView
        adapter = EntryAdapter(requireContext(), entries)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Load data from database
        lifecycleScope.launch {
            (requireActivity().application as BitFitApplication).db.entryDao().getAll().collect { databaseList ->
                entries.clear()
                entries.addAll(databaseList)
                adapter.notifyDataSetChanged()
            }
        }

        // Handle FloatingActionButton click to add a new entry
        binding.fab.setOnClickListener {
            showAddEntryDialog()
        }
    }

    private fun showAddEntryDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_entry, null)
        val foodNameEditText = dialogView.findViewById<EditText>(R.id.foodNameEditText)
        val caloriesEditText = dialogView.findViewById<EditText>(R.id.caloriesEditText)

        AlertDialog.Builder(requireContext())
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
                        (requireActivity().application as BitFitApplication).db.entryDao().insert(newEntry)
                    }
                } else {
                    // Show an error message if input is invalid
                    AlertDialog.Builder(requireContext())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
