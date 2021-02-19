package ua.kpi.comsys.ip8421

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class BookInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_info)
        val fileName = "BooksInfo/" + intent.getStringExtra("isbn13").toString() + ".txt"
        val bookInfo = readBook(this, fileName)?: return
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
            val ims: InputStream = this.assets.open(bookInfo.image.decapitalize())
            val d = Drawable.createFromStream(ims, null)
            findViewById<ImageView>(R.id.id_image).setImageDrawable(d)
        }

        //findViewById<TextView>(R.id.id_image)
    }
}