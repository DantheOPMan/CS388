package com.example.project2wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var wishlistAdapter: WishlistAdapter
    private val wishlistItems = mutableListOf<WishlistItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.wishlistRv)
        wishlistAdapter = WishlistAdapter(wishlistItems)
        recyclerView.adapter = wishlistAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemNameEt = findViewById<EditText>(R.id.itemNameEt)
        val priceEt = findViewById<EditText>(R.id.priceEt)
        val urlEt = findViewById<EditText>(R.id.urlEt)
        val submitBtn = findViewById<Button>(R.id.submitBtn)

        // Add new item to wishlist
        submitBtn.setOnClickListener {
            val name = itemNameEt.text.toString()
            val price = priceEt.text.toString().toDoubleOrNull() ?: 0.0
            val url = urlEt.text.toString()

            if (name.isNotEmpty() && url.isNotEmpty()) {
                val newItem = WishlistItem(name, price, url)
                wishlistItems.add(newItem)
                wishlistAdapter.notifyItemInserted(wishlistItems.size - 1)

                // Clear input fields
                itemNameEt.text.clear()
                priceEt.text.clear()
                urlEt.text.clear()
            }
        }
    }
}
