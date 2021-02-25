package ua.kpi.comsys.ip8421

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_info)
        findViewById<ProgressBar>(R.id.progressBar2).visibility = View.VISIBLE
        val isbn = intent.getStringExtra("isbn13").toString()
        GlobalScope.launch {
            val bookInfo = readBook(isbn)?: return@launch
            withContext(Dispatchers.Main){
                createInfo(bookInfo)
                findViewById<ProgressBar>(R.id.progressBar2).visibility = View.INVISIBLE
                findViewById<ScrollView>(R.id.id_scrollView).visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun createInfo(bookInfo: Book) {
        findViewById<TextView>(R.id.id_title).text = "Title: " + bookInfo.title
        findViewById<TextView>(R.id.id_subtitle).text = "Subtitle: " + bookInfo.subtitle
        findViewById<TextView>(R.id.id_description).text = "Description: " + bookInfo.desc
        findViewById<TextView>(R.id.id_publisher).text = "Publisher: " + bookInfo.publisher
        findViewById<TextView>(R.id.id_pages).text ="Pages: " + bookInfo.pages
        findViewById<TextView>(R.id.id_rating).text ="Rating: " + bookInfo.rating
        findViewById<TextView>(R.id.id_year).text = "Year: " + bookInfo.year
        findViewById<TextView>(R.id.id_authors).text = "Authors: " + bookInfo.authors
        if(bookInfo.image == ""){
            findViewById<ImageView>(R.id.id_image).setImageResource(R.drawable.no_image)
        } else {
            val imageUri = bookInfo.image.decapitalize()
            val ivBasicImage = findViewById<View>(R.id.id_image) as ImageView
            Picasso.get().load(imageUri).into(ivBasicImage)
        }
    }
}

