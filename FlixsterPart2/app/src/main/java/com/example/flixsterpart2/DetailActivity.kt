// DetailActivity.kt
package com.example.flixsterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import android.util.Log
import kotlinx.coroutines.*
import android.content.Intent
import android.net.Uri

private const val TAG = "DetailActivity"
private const val API_KEY = "c30b6be13072568f3198912087cdda39"

class DetailActivity : AppCompatActivity() {

    private lateinit var detailPersonImageView: ImageView
    private lateinit var detailPersonNameTextView: TextView
    private lateinit var detailPersonDepartmentTextView: TextView
    private lateinit var detailPersonBiographyTextView: TextView
    private lateinit var detailPersonBirthdayTextView: TextView
    private lateinit var detailPersonDeathdayTextView: TextView
    private lateinit var detailPersonPlaceOfBirthTextView: TextView
    private lateinit var detailPersonAlsoKnownAsTextView: TextView
    private lateinit var detailPersonHomepageTextView: TextView
    private lateinit var backButton: ImageView // Explicit Back Button

    private val client = OkHttpClient()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Initialize views
        detailPersonImageView = findViewById(R.id.detailPersonImageView)
        detailPersonNameTextView = findViewById(R.id.detailPersonNameTextView)
        detailPersonDepartmentTextView = findViewById(R.id.detailPersonDepartmentTextView)
        detailPersonBiographyTextView = findViewById(R.id.detailPersonBiographyTextView)
        detailPersonBirthdayTextView = findViewById(R.id.detailPersonBirthdayTextView)
        detailPersonDeathdayTextView = findViewById(R.id.detailPersonDeathdayTextView)
        detailPersonPlaceOfBirthTextView = findViewById(R.id.detailPersonPlaceOfBirthTextView)
        detailPersonAlsoKnownAsTextView = findViewById(R.id.detailPersonAlsoKnownAsTextView)
        detailPersonHomepageTextView = findViewById(R.id.detailPersonHomepageTextView)
        backButton = findViewById(R.id.backButton)

        // Set up back button functionality
        backButton.setOnClickListener {
            finish() // Closes the activity and returns to MainActivity
        }

        // Get the Person object from Intent
        val person = intent.getSerializableExtra(PERSON_EXTRA) as? Person

        if (person != null) {
            // Set basic data to views
            detailPersonNameTextView.text = person.name
            detailPersonDepartmentTextView.text = "Department: ${person.knownForDepartment}"

            // Load image with Glide
            Glide.with(this)
                .load(person.profileImageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(detailPersonImageView)

            // Fetch and display detailed information
            fetchPersonDetails(person.id)
        } else {
            // Handle the error case where person data is not available
            detailPersonNameTextView.text = "No Data Available"
            detailPersonDepartmentTextView.text = ""
            detailPersonBiographyTextView.text = ""
            detailPersonBirthdayTextView.text = ""
            detailPersonDeathdayTextView.text = ""
            detailPersonPlaceOfBirthTextView.text = ""
            detailPersonAlsoKnownAsTextView.text = ""
            detailPersonHomepageTextView.text = ""
        }
    }

    private fun fetchPersonDetails(personId: Int) {
        coroutineScope.launch {
            val personDetailsUrl = "https://api.themoviedb.org/3/person/$personId?api_key=$API_KEY"

            try {
                val request = Request.Builder()
                    .url(personDetailsUrl)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        Log.e(TAG, "Unexpected code $response")
                        runOnUiThread {
                            detailPersonBiographyTextView.text = "Biography not available."
                            detailPersonBirthdayTextView.text = "Birthday: N/A"
                            detailPersonDeathdayTextView.text = "Deathday: N/A"
                            detailPersonPlaceOfBirthTextView.text = "Place of Birth: N/A"
                            detailPersonAlsoKnownAsTextView.text = "Also Known As: N/A"
                            detailPersonHomepageTextView.text = "Homepage: N/A"
                        }
                        return@use
                    }

                    val responseData = response.body?.string()
                    if (responseData != null) {
                        Log.d(TAG, "Person Details JSON: $responseData")
                        // Parse JSON
                        val json = Json { ignoreUnknownKeys = true }
                        val detailedPerson = json.decodeFromString<PersonDetails>(responseData)

                        // Update UI on the main thread
                        runOnUiThread {
                            detailPersonBiographyTextView.text = "Biography: ${detailedPerson.biography ?: "N/A"}"
                            detailPersonBirthdayTextView.text = "Birthday: ${detailedPerson.birthday ?: "N/A"}"
                            detailPersonDeathdayTextView.text = "Deathday: ${detailedPerson.deathday ?: "N/A"}"
                            detailPersonPlaceOfBirthTextView.text = "Place of Birth: ${detailedPerson.placeOfBirth ?: "N/A"}"

                            // Also Known As
                            if (!detailedPerson.alsoKnownAs.isNullOrEmpty()) {
                                detailPersonAlsoKnownAsTextView.text = "Also Known As: ${detailedPerson.alsoKnownAs.joinToString(", ")}"
                            } else {
                                detailPersonAlsoKnownAsTextView.text = "Also Known As: N/A"
                            }

                            // Homepage
                            if (!detailedPerson.homepage.isNullOrEmpty()) {
                                detailPersonHomepageTextView.text = "Homepage: ${detailedPerson.homepage}"
                                detailPersonHomepageTextView.setOnClickListener {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailedPerson.homepage))
                                    startActivity(intent)
                                }
                            } else {
                                detailPersonHomepageTextView.text = "Homepage: N/A"
                            }

                            // IMDb Link
                            if (!detailedPerson.imdbId.isNullOrEmpty()) {
                                val imdbUrl = "https://www.imdb.com/name/${detailedPerson.imdbId}/"
                                // Create a clickable link
                                val imdbText = "IMDb: $imdbUrl"
                                detailPersonHomepageTextView.append("\n$imdbText")
                                detailPersonHomepageTextView.setOnClickListener {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception fetching person details: $e")
                runOnUiThread {
                    detailPersonBiographyTextView.text = "Error fetching biography."
                    detailPersonBirthdayTextView.text = "Birthday: N/A"
                    detailPersonDeathdayTextView.text = "Deathday: N/A"
                    detailPersonPlaceOfBirthTextView.text = "Place of Birth: N/A"
                    detailPersonAlsoKnownAsTextView.text = "Also Known As: N/A"
                    detailPersonHomepageTextView.text = "Homepage: N/A"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
