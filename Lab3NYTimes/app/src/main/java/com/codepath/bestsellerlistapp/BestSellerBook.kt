package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single book from the NY Times API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class BestSellerBook {
    @SerializedName("rank")
    var rank = 0

    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("author")
    var author: String? = null

    @SerializedName("book_image")
    var bookImageUrl: String? = null

    @SerializedName("description")
    var description: String? = null

    // Array to hold buy links
    @SerializedName("buy_links")
    var buyLinks: List<BuyLink>? = null

    // Store the Amazon URL directly
    var amazonUrl: String? = null

    // Function to extract the Amazon URL from the buy_links array
    fun setAmazonUrl() {
        amazonUrl = buyLinks?.firstOrNull { it.name == "Amazon" }?.url
    }
}

class BuyLink {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("url")
    var url: String? = null
}