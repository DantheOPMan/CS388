package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bylineTextView: TextView
    private lateinit var abstractTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        bylineTextView = findViewById(R.id.mediaByline)
        abstractTextView = findViewById(R.id.mediaAbstract)

        // Get the extra as Article
        val article = intent.getSerializableExtra(ARTICLE_EXTRA) as? DisplayArticle

        if (article != null) {
            titleTextView.text = article.headline
            bylineTextView.text = article.byline

            abstractTextView.text = article.abstract

            if (!article.mediaImageUrl.isNullOrEmpty()) {
                Glide.with(this)
                    .load(article.mediaImageUrl)
                    .into(mediaImageView)
            }
        } else {
            Log.e(TAG, "Article data is missing")
            // Optionally, handle the missing data scenario (e.g., show an error message)
        }
    }
}