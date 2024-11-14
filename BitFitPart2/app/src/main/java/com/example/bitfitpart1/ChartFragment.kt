package com.example.bitfitpart1

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bitfitpart1.databinding.FragmentChartsBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch

class ChartsFragment : Fragment() {
    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChartsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadCalorieChart()
    }

    private fun loadCalorieChart() {
        lifecycleScope.launch {
            val entriesList = (requireActivity().application as BitFitApplication).db.entryDao().getAllEntries()
            if (entriesList.isNotEmpty()) {
                val lineEntries = mutableListOf<Entry>()
                // For demonstration, let's assume each entry's position in the list is the x-value
                // and the calorie count is the y-value.
                for ((index, entry) in entriesList.withIndex()) {
                    lineEntries.add(Entry(index.toFloat(), entry.calories.toFloat()))
                }

                val dataSet = LineDataSet(lineEntries, "Calorie Intake Over Time")
                dataSet.color = Color.BLUE
                dataSet.valueTextColor = Color.BLACK

                val lineData = LineData(dataSet)
                binding.calorieChart.data = lineData
                binding.calorieChart.description.text = "Daily Calorie Trends"
                binding.calorieChart.invalidate()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
