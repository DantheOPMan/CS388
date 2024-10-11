// MainActivity.kt
package com.example.flixsterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flixsterpart2.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private const val TAG = "MainActivity"
private const val API_KEY = "c30b6be13072568f3198912087cdda39"
private const val PERSON_POPULAR_URL = "https://api.themoviedb.org/3/person/popular?api_key=$API_KEY"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val persons = mutableListOf<Person>()
    private lateinit var adapter: PersonAdapter
    private val client = OkHttpClient()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        adapter = PersonAdapter(this, persons)
        binding.personsRecyclerView.adapter = adapter

        // Set LayoutManager to Grid with 2 columns
        binding.personsRecyclerView.layoutManager = GridLayoutManager(this, 2)

        // Fetch popular persons
        fetchPopularPersons()
    }

    private fun fetchPopularPersons() {
        coroutineScope.launch {
            try {
                val request = Request.Builder()
                    .url(PERSON_POPULAR_URL)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        Log.e(TAG, "Unexpected code $response")
                        return@use
                    }

                    val responseData = response.body?.string()
                    if (responseData != null) {
                        Log.d(TAG, "JSON Response: $responseData")
                        // Parse JSON
                        val json = Json { ignoreUnknownKeys = true }
                        val personResponse = json.decodeFromString<PersonResponse>(responseData)

                        Log.d(TAG, "Fetched data: $personResponse")

                        withContext(Dispatchers.Main) {
                            persons.addAll(personResponse.results)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception fetching data: $e")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel() // Cancel all coroutines when activity is destroyed
    }
}
