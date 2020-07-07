package com.lordsam.newzify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.card_news.view.*
import org.json.JSONArray
import org.json.JSONObject
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private val apiKey = "de02afe1112f4ea09a8bf6fd0f6deec2"
    private lateinit var country: String
    private lateinit var url: String
    private lateinit var lv: ListView
    private lateinit var arrayOfNews: JSONArray
    private lateinit var queue: RequestQueue
    private lateinit var jsonObj: JsonObjectRequest
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.listViewMain)
        country = "in"

        getNews(country)
    }

    override fun onStart() {
        super.onStart()

        getNews(country)
    }

    private fun getNews(country: String) {
        url =
            "https://newsapi.org/v2/top-headlines?country=$country&category=business&apiKey=$apiKey"
        queue = Volley.newRequestQueue(this)

        jsonObj = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                arrayOfNews = response.getJSONArray("articles")
                newsAdapter = NewsAdapter(this, arrayOfNews)
                lv.adapter = newsAdapter
            },
            Response.ErrorListener {
                Log.i("responseNews", "error")
            })
        queue.add(jsonObj)
    }

    inner class NewsAdapter(context: Context, arrayOfNews: JSONArray) : BaseAdapter() {

        private var mContext: Context? = null
        private var arrOfNews: JSONArray? = null

        init {
            this.mContext = context
            this.arrOfNews = arrayOfNews

        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val layoutInflater =
                mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val newsCard = layoutInflater.inflate(R.layout.card_news, null, true)
            val news = arrOfNews!![p0] as JSONObject
            val author = news.getString("author")
            val source = news.getJSONObject("source")
            val name = source.getString("name")
            val title = news.getString("title")
            val content = news.getString("content")
            val des = news.getString("description")
            val publishedAt = news.getString("publishedAt")
            val imgPath = news.get("urlToImage").toString()

            if (author != "null") {
                newsCard.cardAuthor.text = author
            } else {
                newsCard.cardAuthor.text = "Unknown"
            }

            if (title != "null") {
                newsCard.cardTitle.text = title
            } else {
                newsCard.cardTitle.text = "Empty"
            }

            if (imgPath != "null") {
                Glide.with(this@MainActivity)
                    .load(imgPath)
                    .into(newsCard.imageCard)
            } else {
                newsCard.imageCard.setImageResource(R.drawable.not_found)
            }

            newsCard.setOnClickListener {
                val intent = Intent(mContext, NewsActivity::class.java)
                intent.putExtra("source", name)
                intent.putExtra("img", imgPath)
                intent.putExtra("title", title)
                intent.putExtra("des", des)
                intent.putExtra("content", content)
                intent.putExtra("author", author)
                intent.putExtra("publishedAt", publishedAt)
                mContext!!.startActivity(intent)
            }

            return newsCard
        }

        override fun getItem(p0: Int): Any {
            return arrayOfNews[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return arrOfNews!!.length()
        }

    }
}