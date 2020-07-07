package com.lordsam.newzify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.json.JSONObject

class NewsActivity : AppCompatActivity() {

    private lateinit var txtSource :TextView
    private lateinit var img :ImageView
    private lateinit var txtTitle :TextView
    private lateinit var txtDes :TextView
    private lateinit var txtContent :TextView
    private lateinit var txtAuthor :TextView
    private lateinit var txtDate :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        txtSource = findViewById(R.id.textViewNewsSource)
        img = findViewById(R.id.imageViewNewsPic)
        txtTitle = findViewById(R.id.textViewNewsTitle)
        txtDes = findViewById(R.id.textViewNewsDes)
        txtContent = findViewById(R.id.textViewNewsContent)
        txtAuthor = findViewById(R.id.textViewNewsAuthor)
        txtDate = findViewById(R.id.textViewNewsDate)

        val bundle = intent.extras!!
        val source = bundle.getString("source")
        val imgPath = bundle.getString("img")
        val title = bundle.getString("title")
        val  des = bundle.getString("des")
        val content = bundle.getString("content")
        val author = bundle.getString("author")
        val publishedAt = bundle.getString("publishedAt")

        txtSource.text = source
        Glide.with(this)
            .load(imgPath)
            .into(img)
        txtTitle.text = title
        txtDes.text = des
        txtContent.text = content
        txtAuthor.text = "-$author"
        txtDate.text = "publishedAt :$publishedAt"

    }
}