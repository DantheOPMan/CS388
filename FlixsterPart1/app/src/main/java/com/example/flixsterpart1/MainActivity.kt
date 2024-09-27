package com.example.flixsterpart1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the savedInstanceState is null to avoid re-adding the fragment
        if (savedInstanceState == null) {
            // Add the MovieListFragment to the activity
            supportFragmentManager.beginTransaction()
                .replace(R.id.content, MovieListFragment())
                .commit()
        }
    }
}